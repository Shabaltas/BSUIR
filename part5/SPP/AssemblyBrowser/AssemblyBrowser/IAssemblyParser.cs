using System;
using System.Collections.Generic;
using System.Text;
using AssemblyBrowserLogic.structures;

namespace AssemblyBrowserLogic
{
    interface IAssemblyParser
    {
        public AssemblyStructure ParseAssembly(string path);
    }
}
