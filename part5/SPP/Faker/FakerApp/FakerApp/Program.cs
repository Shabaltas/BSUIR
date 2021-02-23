using System;
using System.Collections.Generic;
using System.Threading;
using Faker;
using FakerApp.Properties;

namespace FakerApp
{
     struct MyStruct
    {
        public int i;
        public Bar bar;
        public override string ToString()
        {
            return "MyStruct: i = " + i +", bar = " + bar;
        }
    }
    internal class Program
    {
        public static void Main(string[] args)
        {
            FakerConfig config = new FakerConfig();
            config.Add<Bar, int, BarIisGenerator>(bar => bar.isS);
            Faker.Faker faker = new Faker.Faker(config);
            /*foreach (var generator in faker.Generators)
            {
                Console.WriteLine(generator.Key + " : " + generator.Value);
            }*/
            /*Console.WriteLine("bool: " + faker.Create(typeof(bool)));
            Console.WriteLine("byte: " + faker.Create(typeof(byte))); 
            Console.WriteLine("char: " + faker.Create(typeof(char))); 
            Console.WriteLine("double: " + faker.Create(typeof(double)));
            Console.WriteLine("float: " + faker.Create(typeof(float))); 
            Console.WriteLine("int: " + faker.Create(typeof(int))); 
            Console.WriteLine("long: " + faker.Create(typeof(long))); 
            Console.WriteLine("short: " + faker.Create(typeof(short))); 
            Console.WriteLine("string: " + faker.Create(typeof(string))); 
            Console.WriteLine("MuEnum: " + faker.Create(typeof(MyEnum)));
            Console.WriteLine("MyStruct: " + faker.Create(typeof(MyStruct)));
            List<int> list = (List<int>)faker.Create(typeof(List<int>));
            foreach (var VARIABLE in list)
            {
                Console.WriteLine(VARIABLE);
            }
           
            Console.WriteLine(faker.Create(typeof(Bar)));
            Console.WriteLine(faker.Create(typeof(MyGeneric<>)));*/
            /*Console.WriteLine(faker.Create(typeof(bool)));
            Console.WriteLine(faker.Create(typeof(MyEnum)));
            MyStruct myStruct = new MyStruct();
            Console.WriteLine(myStruct);
            */

            Console.WriteLine(faker.Create<Bar>());
            /*faker.Create<Foo>();
            faker.Create<Bar>();*/
        }
    }
}