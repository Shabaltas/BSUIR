using System;
using System.Collections.Generic;
using System.Text;
using AssemblyBrowserLogic;
using AssemblyBrowserLogic.structures;

namespace AssemblyBrowserApp.model.browser
{
    class BrowserService : IBrowserService
    {
        public List<BaseStructure> OpenAndParse(string filename)
        {
            Dictionary<string, BaseStructure> parsedResult = new AssemblyParser().ParseAssembly(filename).Content;
            BaseStructure[] result = new BaseStructure[parsedResult.Count];
            parsedResult.Values.CopyTo(result, 0);
            return new List<BaseStructure>(result);
        }
    }
}
