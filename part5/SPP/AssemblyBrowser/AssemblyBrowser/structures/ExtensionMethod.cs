using System;
using System.Collections.Generic;
using System.Text;

namespace AssemblyBrowserLogic.structures
{
    public class ExtensionMethod
    {
        public MethodStructure Method { get; set; }
        public Type TypeFrom { get; set; }
        public Type TypeTo { get; set; }
    }
}
