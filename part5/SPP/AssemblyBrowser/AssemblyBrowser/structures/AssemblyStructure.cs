using System;
using System.Collections.Generic;
using System.Text;

namespace AssemblyBrowserLogic.structures
{
    public class AssemblyStructure 
    {
        public Dictionary<string, BaseStructure> Content { get; private set; }
        public string Declaration { get; private set; }
        public AssemblyStructure(string _name)
        {
            this.Declaration = _name;
            this.Content = new Dictionary<string, BaseStructure>();
        }
        public bool AddStructure(BaseStructure _baseStructure)
        {
            if (this.Content.ContainsKey(_baseStructure.Declaration))
                return false;
            this.Content.Add(_baseStructure.Declaration, _baseStructure);
            return true;
        }
    }
}
