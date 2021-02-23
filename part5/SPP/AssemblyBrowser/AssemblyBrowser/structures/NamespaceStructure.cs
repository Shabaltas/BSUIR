using System;
using System.Collections.Generic;
using System.Text;

namespace AssemblyBrowserLogic.structures
{
    public class NamespaceStructure : BaseStructure
    {
        //public List<TypeStructure> Types { get; private set; }
        public NamespaceStructure(string _declaration)
        {
            this.Declaration = _declaration;
            this.NestedStructures = new List<BaseStructure>();
        }
        public void AddType(TypeStructure _newType)
        {
            this.NestedStructures.Add(_newType);
        }

        public override bool Equals(object obj)
        {
            return obj != null && obj is NamespaceStructure structure &&
                   this.Declaration.Equals(((NamespaceStructure)obj).Declaration);
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(NestedStructures);
        }
    }
}
