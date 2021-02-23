using System;
using AssemblyBrowserLogic;
using AssemblyBrowserLogic.structures;

namespace ConsoleApp
{
    class Program
    {
        static void Main(string[] args)
        {
            AssemblyStructure assembly = new AssemblyParser().ParseAssembly(@"C:\Users\Asus\BSUIRtheory\part5\СПП\AssemblyBrowser\TestProject\bin\Debug\netstandard2.0\TestProject.dll");
            foreach (var @namespace in assembly.Content)
            {
                Console.WriteLine(@namespace.Key);
                if (@namespace.Value is NamespaceStructure)
                {
                    foreach (var type in (@namespace.Value as NamespaceStructure).NestedStructures)
                    {
                        Console.WriteLine("   " + type.Declaration);
                        foreach (BaseStructure field in type.NestedStructures)
                        {
                            Console.WriteLine("      " + field.Declaration);

                        }
                    }
                } else
                {
                    Console.WriteLine(@namespace.Value.Declaration);
                    TypeStructure thisType = @namespace.Value as TypeStructure;
                    foreach (FieldStructure field in thisType.NestedStructures)
                    {
                        Console.WriteLine("   " + field.Declaration);
                    }
                   
                }
            }
            Console.ReadKey();
        }
    }
}
