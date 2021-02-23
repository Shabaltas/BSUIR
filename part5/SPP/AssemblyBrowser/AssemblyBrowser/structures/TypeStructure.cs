using System;
using System.Collections.Generic;
using System.Text;

namespace AssemblyBrowserLogic.structures
{
    public class TypeStructure : BaseStructure
    {
        public TypeStructure(string _declaration)
        {
            this.Declaration = _declaration;
        }
        public void AddField(FieldStructure _newField)
        {
            this.NestedStructures.Add(_newField);
        }
        public void AddProperty(PropertyStructure _newProperty)
        {
            this.NestedStructures.Add(_newProperty);
        }
        public void AddMethod(MethodStructure _newMethod)
        {
            this.NestedStructures.Add(_newMethod);
        }
    }
}
