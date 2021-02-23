using System;
using System.Collections.Generic;
using System.Text;

namespace AssemblyBrowserLogic.structures
{
    public class PropertyStructure : BaseStructure
    {
        public PropertyStructure(string _declaration, MethodStructure _getMethod, MethodStructure _setMethod)
        {
            this.Declaration = _declaration;
            this.NestedStructures.Add(_getMethod);
            this.NestedStructures.Add(_setMethod);
        }
        public PropertyStructure(string _declaration)
        {
            this.Declaration = _declaration;
        }
    }
}
