using System;
using System.Reflection;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using AssemblyBrowserLogic;
using AssemblyBrowserLogic.structures;
using System.Collections.Generic;
using System.Linq;

namespace AssemblyBrowserTests
{
    [TestClass]
    public class AssemblyBrowserTest
    {
        private string path = @"..\TestProject\bin\Debug\netstandard2.0\TestProject.dll";
        AssemblyStructure assemblyStructure;
        Assembly assembly;
        private string expectedNamespace;
        private Type[] expectedTypes;

        [TestInitialize]
        public void setup()
        {
            assemblyStructure = new AssemblyParser().ParseAssembly(path);
            assembly = Assembly.LoadFile(path);
            expectedTypes = assembly.GetTypes();
            expectedNamespace = expectedTypes[0].Namespace;
        }
        [TestMethod]
        public void NamespaceTest()
        {
            //expectedNamespace = assemblyStructure.NestedStructures[0].Declaration;
            Assert.IsTrue(assemblyStructure.Content.ContainsKey(expectedNamespace));
        }

        [TestMethod]
        public void TypesTest()
        {
            List<BaseStructure> actualTypes = assemblyStructure.Content.Values.ElementAt(0).NestedStructures;
            Assert.AreEqual(expectedTypes.Length, actualTypes.Count);
            Assert.AreEqual(assemblyStructure.Content.Values.ElementAt(0).Declaration, expectedNamespace);
            foreach (var expectedType in expectedTypes)
            {
                var query = from actualType in actualTypes
                            where actualType.Declaration.EndsWith(expectedType.Name)
                            select actualType;
                Assert.AreEqual(1, query.Count());
            }
        }

        [TestMethod]
        public void ExtensionMethodName()
        {
            List<BaseStructure> actualTypes = assemblyStructure.Content.Values.ElementAt(0).NestedStructures;
            var query = from actualType in actualTypes
                        where actualType.Declaration.Contains("static")
                        select actualType;
            BaseStructure staticExtType = query.First();
            query = from actualType in actualTypes
                    where !actualType.Declaration.Contains("static")
                    select actualType;
            BaseStructure type = query.First();
            Assert.IsNotNull(type.NestedStructures.Find(method => method.Declaration.Contains("ExtMethod")));
            Assert.IsNull(staticExtType.NestedStructures.Find(method => method.Declaration.Contains("ExtMethod"))); 
        }
    }
}
