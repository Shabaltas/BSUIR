using System;
using System.Collections.Generic;
using System.Text;

namespace AssemblyBrowserLogic.structures
{
    public class MethodStructure : BaseStructure
    {
        public bool IsExtension { get; set; }
        public LinkedList<string> Params { get; private set; }
        public MethodStructure(string _declaration, bool _isExtension)
        {
            this.Declaration = _declaration;
            this.IsExtension = _isExtension;
            this.Params = new LinkedList<string>();
        }
        public MethodStructure(string _declaration)
        {
            this.Declaration = _declaration;
            this.IsExtension = false;
            this.Params = new LinkedList<string>();
        }

        public void AddParam(string _paramDeclaration)
        {
            this.Params.AddLast(_paramDeclaration);
        }

        protected override string FullDeclaration()
        {
            StringBuilder result = new StringBuilder(_declaration).Append("(");
            LinkedListNode<string> param = Params.First;
            for (int i = 0; i < Params.Count-1; i++)
            {
                result.Append(param.Value).Append(", ");
                param = param.Next;
            }
            return result.Append(param?.Value).Append(")").Append(IsExtension?"*":"").ToString();
        }
    }
}
