using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;
using System.Linq;
using AssemblyBrowserLogic.structures;
using System.Runtime.CompilerServices;

namespace AssemblyBrowserLogic
{
    public class AssemblyParser : IAssemblyParser
    {
        private List<ExtensionMethod> _extensionMethods = new List<ExtensionMethod>();
        public AssemblyStructure ParseAssembly(string path)
        {
            var assembly = Assembly.LoadFile(path);
            AssemblyStructure assemblyStructure = new AssemblyStructure(assembly.GetName().Name);
            var types = assembly.GetTypes();
            findExtensionMethods(types);
            foreach (var type in types)
            {
                string typeNamespace = type.Namespace;
                TypeStructure newTypeStructure = ParseType(type);
                BaseStructure baseStructure;
                if (typeNamespace == null) 
                    baseStructure = newTypeStructure;
                else
                {
                    if (!assemblyStructure.Content.TryGetValue(typeNamespace, out baseStructure))
                    {
                        baseStructure = new NamespaceStructure(typeNamespace);
                    };
                    (baseStructure as NamespaceStructure).AddType(newTypeStructure);
                }
                assemblyStructure.AddStructure(baseStructure);
            }
            return assemblyStructure;
        }

        private void findExtensionMethods(Type[] types)
        {
            var query = from type in types
                            where type.IsSealed && type.IsAbstract && !type.IsNested
                            from method in type.GetMethods(BindingFlags.Static | BindingFlags.Public | BindingFlags.NonPublic)
                            where method.IsDefined(typeof(ExtensionAttribute), false)
                            where method.GetParameters().Length > 0
                            select method;
            MethodInfo[] extMethods = query.ToArray<MethodInfo>();
            foreach(MethodInfo extMethodInfo in extMethods)
            {
                ExtensionMethod extMethod = new ExtensionMethod();
                extMethod.Method = ParseMethod(extMethodInfo, true);
                extMethod.TypeFrom = extMethodInfo.DeclaringType;
                extMethod.TypeTo = extMethodInfo.GetParameters()[0].ParameterType;
                _extensionMethods.Add(extMethod);
            } 
        }


        private TypeStructure ParseType(Type type)
        {
            StringBuilder declaration = new StringBuilder();
            if (type.IsNestedPublic || type.IsPublic)
                declaration.Append("public ");
            else if (type.IsNestedPrivate)
                declaration.Append("private ");
            else if (type.IsNestedFamily)
                declaration.Append("protected ");
            else if (type.IsNestedAssembly)
                declaration.Append("internal ");
            else if (type.IsNestedFamORAssem)
                declaration.Append("protected internal ");
            else if (type.IsNestedFamANDAssem)
                declaration.Append("private protected ");
            else if (type.IsNotPublic)
                declaration.Append("private ");

            if (type.IsAbstract && type.IsSealed)
                declaration.Append("static ");
            else if (type.IsAbstract)
                declaration.Append("abstract ");
            else if (type.IsSealed)
                declaration.Append("sealed ");

            if (type.IsClass)
                declaration.Append("class ");
            else if (type.IsEnum)
                declaration.Append("enum ");
            else if (type.IsInterface)
                declaration.Append("interface ");
            else if (type.IsGenericType)
                declaration.Append("generic ");
            else if (type.IsValueType && !type.IsPrimitive)
                declaration.Append("struct ");

            declaration.Append(type.Name);

            if (type.IsGenericType)
            {
                declaration.Append(parseGenericArgs(type.GetGenericArguments()));
            }

            TypeStructure typeStructure = new TypeStructure(declaration.ToString());
            foreach (var constructor in type.GetConstructors(BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Instance))
            {
                typeStructure.AddMethod(ParseConstructor(constructor));
            }
            foreach (var fieldInfo in type.GetFields(BindingFlags.Public | BindingFlags.Static | BindingFlags.NonPublic | BindingFlags.Instance))
            {
                typeStructure.AddField(ParseField(fieldInfo));
            }
            foreach (var propertyInfo in type.GetProperties(BindingFlags.Public | BindingFlags.Static | BindingFlags.NonPublic | BindingFlags.Instance))
            {
                typeStructure.AddProperty(ParseProperty(propertyInfo));
            }
            foreach (var methodInfo in type.GetMethods(BindingFlags.Public | BindingFlags.Static | BindingFlags.NonPublic | BindingFlags.Instance))
            {
                if (methodInfo.IsSpecialName) continue;
                MethodStructure method = ParseMethod(methodInfo, true);
                var queryIfEmpty = from extensionMethod in _extensionMethods
                        where extensionMethod.TypeFrom.Equals(type) && extensionMethod.Method.Declaration.Equals(method.Declaration)
                        select extensionMethod;
                if (queryIfEmpty.ToArray<ExtensionMethod>().Length == 0)
                {
                    method.IsExtension = false;
                    typeStructure.AddMethod(method);
                }
            }
            var query = from extensionMethod in _extensionMethods
                    where extensionMethod.TypeTo.Equals(type)
                    select extensionMethod.Method;
            foreach (MethodStructure extMethod in query.ToArray<MethodStructure>())
            {
                typeStructure.AddMethod(extMethod);
            }
            return typeStructure;
        }


        private FieldStructure ParseField(FieldInfo fieldInfo)
        {
            var declaration = new StringBuilder();
            if (fieldInfo.IsAssembly)
                declaration.Append("internal ");
            else if (fieldInfo.IsFamily)
                declaration.Append("protected ");
            else if (fieldInfo.IsFamilyOrAssembly)
                declaration.Append("protected internal ");
            else if (fieldInfo.IsFamilyAndAssembly)
                declaration.Append("private protected ");
            else if (fieldInfo.IsPrivate)
                declaration.Append("private ");
            else if (fieldInfo.IsPublic)
                declaration.Append("public ");
            if (fieldInfo.IsInitOnly)
                declaration.Append("readonly ");
            if (fieldInfo.IsStatic)
                declaration.Append("static ");
            Type fieldType = fieldInfo.FieldType;
            declaration.Append(fieldType.Name)
                 .Append(fieldType.IsGenericType ? parseGenericArgs(fieldType.GetGenericArguments()) : "").Append(" ").Append(fieldInfo.Name);
            return new FieldStructure(declaration.ToString());
        }

        private PropertyStructure ParseProperty(PropertyInfo propertyInfo)
        {
            Type propertyType = propertyInfo.PropertyType;
            string declaration = propertyType.Name + (propertyType.IsGenericType?parseGenericArgs(propertyType.GetGenericArguments()):"") + " " + propertyInfo.Name;
            MethodInfo setMethodInfo = propertyInfo.GetSetMethod(true);
            MethodInfo getMethodInfo = propertyInfo.GetGetMethod(true);
            MethodStructure getMethod = getMethodInfo != null 
                 ? new MethodStructure(getMethodModifiers(getMethodInfo)
                                       + " " + getMethodInfo.ReturnType.Name
                                       + " " + getMethodInfo.Name) 
                 : null;
            MethodStructure setMethod = null;
            if (setMethodInfo != null)
            {
                setMethod = new MethodStructure(getMethodModifiers(setMethodInfo)
                                       + " " + setMethodInfo.ReturnType.Name
                                       + " " + setMethodInfo.Name);
                ParameterInfo param = setMethodInfo.GetParameters()[0];
                Type paramType = param.ParameterType;
                setMethod.AddParam(paramType.Name + (paramType.IsGenericType?parseGenericArgs(paramType.GetGenericArguments()):"") + " " + param.Name);
            }
           
            return new PropertyStructure(declaration, getMethod, setMethod);
        }

        private MethodStructure ParseMethod(MethodInfo methodInfo, Boolean isExtension)
        {
            Type returnType = methodInfo.ReturnType;
            StringBuilder declaration = new StringBuilder()
                .Append(getMethodModifiers(methodInfo))
                .Append(" ").Append(returnType.Name)
                .Append(returnType.IsGenericType ? parseGenericArgs(returnType.GetGenericArguments()) : "")
                .Append(" ")
                .Append(methodInfo.Name);
            if (methodInfo.IsGenericMethod)
            {
                declaration.Append(parseGenericArgs(methodInfo.GetGenericArguments()));
            }
            MethodStructure method = new MethodStructure(declaration.ToString(), isExtension);
            ParameterInfo[] parameters = methodInfo.GetParameters();
            for (int i = isExtension ? 1 : 0; i < parameters.Length; i++) {
                ParameterInfo param = parameters[i];
                Type paramType = param.ParameterType;
                method.Params.AddLast(new StringBuilder(paramType.Name)
                    .Append(paramType.IsGenericType ? parseGenericArgs(paramType.GetGenericArguments()) : "").Append(" ").Append(param.Name).ToString());
            }
            return method;
        }

        private string parseGenericArgs(Type[] genericArgs)
        {
            StringBuilder declaration = new StringBuilder();
            declaration.Append("<").Append(genericArgs[0].Name);
            for (int i = 1; i < genericArgs.Length; i++)
            {
                declaration.Append(", ").Append(genericArgs[i].Name);
            }
            declaration.Append(">");
            return declaration.ToString();
        }

        private MethodStructure ParseConstructor(ConstructorInfo constructorInfo)
        {
            StringBuilder declaration = new StringBuilder()
                .Append(getMethodModifiers(constructorInfo))
                .Append(" ")
                .Append(constructorInfo.DeclaringType.Name);
            if (constructorInfo.IsGenericMethod)
            {
                declaration.Append(parseGenericArgs(constructorInfo.GetGenericArguments()));
            }
            MethodStructure method = new MethodStructure(declaration.ToString(), false);
            ParameterInfo[] parameters = constructorInfo.GetParameters();
            foreach (ParameterInfo param in parameters)
            {
                Type paramType = param.ParameterType;
                method.Params.AddLast(new StringBuilder(paramType.Name)
                    .Append(paramType.IsGenericType ? parseGenericArgs(paramType.GetGenericArguments()) : "").Append(" ").Append(param.Name).ToString());
            }
            return method;
        }

        private string getMethodModifiers(MethodBase methodInfo)
        {

            StringBuilder result = new StringBuilder();
            if (methodInfo.IsAssembly)
                result.Append("internal ");
            else if (methodInfo.IsFamily)
                result.Append("protected ");
            else if (methodInfo.IsFamilyOrAssembly)
                result.Append("protected internal ");
            else if (methodInfo.IsFamilyAndAssembly)
                result.Append("private protected ");
            else if (methodInfo.IsPrivate)
                result.Append("private ");
            else if (methodInfo.IsPublic)
                result.Append("public ");
            if (methodInfo.IsStatic)
                result.Append("static ");
            return result.ToString();
        }
    }
}
