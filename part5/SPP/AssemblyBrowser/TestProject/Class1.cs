using System;
using System.Collections.Generic;
using System.Text;
namespace TestProject
{
    public static class Class1
    {
        private static Int32 i = 1;
        
        public static string ExtMethod(this Class2 str)
        {
            return new StringBuilder().Append(i++).ToString();
        }
        public static void OutString(this string str)
        {
            Console.WriteLine(str);
        }
        public static List<String> getSomeString()
        {
            return new List<String>();
        }
    }
}
