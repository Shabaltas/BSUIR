using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;

namespace One{
	namespace inner{
	public class BarClass
    {
        public void MyPublicMethod()
        {

        }

        public void MyPublicMethod(int i)
        {
        	
        }

        internal void MyInternalMethod()
        {

        }
    }
}
}
namespace Two
{
    public class FooClass
    {
        public void MyPublicMethod()
        {

        }

        private void MyPrivateMethod()
        {

        }
    }

    
}

