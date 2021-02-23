using System;
using System.Collections.Generic;
using System.Text;

namespace AssemblyBrowserLogic.structures
{
    public class BaseStructure
    {
        protected string _declaration;
        public string Declaration {
            get { 
                return FullDeclaration();  
            }
            protected set { 
                _declaration = value;
            } 
        }
        protected virtual string FullDeclaration() { return _declaration; }

        public List<BaseStructure> NestedStructures { get; set; } = new List<BaseStructure>();
    }
}
