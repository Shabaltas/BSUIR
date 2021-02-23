using System;
using System.Collections.Generic;
using System.Text;
using AssemblyBrowserLogic.structures;

namespace AssemblyBrowserApp.model.browser
{
    public interface IBrowserService
    {
        public List<BaseStructure> OpenAndParse(string filename);
    }
}
