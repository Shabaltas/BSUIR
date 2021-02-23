using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TestsGenerator;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            var tg = new MSTestsGenerator(5, 5, 5);
            Console.WriteLine("Main thread start: " + Thread.CurrentThread.ManagedThreadId);
            var r = tg.GenerateTests(new[]
            {
                @"C:\Users\Asus\BSUIRtheory\part5\СПП\Tracer\SimpleSerializer\JSonSerializer.cs",
                @"C:\Users\Asus\BSUIRtheory\part5\СПП\Tracer\SimpleSerializer\CustomXmlSerializer.cs"
            }, @"C:\Users\Asus\BSUIRtheory\part5\СПП\TestsGenerator\GeneratedTests");  
            r.Wait();
            Console.WriteLine("Main thread end: " + Thread.CurrentThread.ManagedThreadId);
            Console.ReadKey();
        }
    }
}
