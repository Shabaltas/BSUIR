using System;
using System.Collections.Generic;
using System.Text;

namespace TestProject
{
    public class Class2
    {
        private int _field;
        public int Field
        {
            get
            {
                return _field;
            }
            set
            {
                _field = value;
            }
        }
        public Class2() { }
        void method<T>(T i)
        {
            return;
        }
    }
}
