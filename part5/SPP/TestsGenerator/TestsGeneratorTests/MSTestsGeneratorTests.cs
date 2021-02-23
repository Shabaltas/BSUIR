using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks; 
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TestsGenerator;

namespace TestsGeneratorTests
{
    [TestClass]
    public class MSTestsGeneratorTests
    {
        private readonly string[] _filesForTest1 = { @"..\..\FilesForTests\MyClassFile.cs", @"..\..\FilesForTests\ErrorClass.cs" };
        private readonly string[] _filesForTest = { @"..\..\FilesForTests\SimpleClass.cs"  };
        private readonly string _folderForTests = @"..\..\TestsFolder\";
        private MSTestsGenerator testsGenerator;
        private Task waiter;

        [TestInitialize]
        public void init()
        {
            CleanFolder(_folderForTests);
            testsGenerator = new MSTestsGenerator(3, 4, 5);
            waiter = testsGenerator.GenerateTests( _filesForTest , _folderForTests);
        }
        [TestMethod]
        public void FileNameTest()
        {
            waiter.Wait();
            var testFiles = Directory.GetFiles(_folderForTests).Select(Path.GetFileName);
            Assert.IsTrue(testFiles.Contains("BarClassTest.cs"));
            Assert.IsTrue(testFiles.Contains("FooClassTest.cs"));
            //Assert.IsTrue(testFiles.Contains("ErrorClassTest.cs"));
            Assert.AreEqual(2, testFiles.ToArray().Length);
        }

        [TestMethod]
        public void NoSyntaxErrorTest()
        {
            waiter.Wait();
            var testFile = Directory.GetFiles(_folderForTests).First(file => Path.GetFileName(file) == "BarClassTest.cs");
            var text = GetText(testFile);
            var tree = CSharpSyntaxTree.ParseText(text);
            var diagnostics = tree.GetDiagnostics().FirstOrDefault(n => n.Severity == DiagnosticSeverity.Error);
            Assert.IsNull(diagnostics);
        }

        [TestMethod]
        public void ClassNameTest()
        {
            waiter.Wait();
            var testFile = Directory.GetFiles(_folderForTests).First(file => Path.GetFileName(file) == "BarClassTest.cs");
            var text = GetText(testFile);
            var tree = CSharpSyntaxTree.ParseText(text);
            var root = tree.GetRoot();
            var @class = root.DescendantNodes().OfType<ClassDeclarationSyntax>().First();
            Assert.AreEqual("BarClassTest", @class.Identifier.ValueText);
        }

        [TestMethod]
        public void MethodNameTest()
        {
            waiter.Wait();
            var testFile = Directory.GetFiles(_folderForTests).First(file => Path.GetFileName(file) == "BarClassTest.cs");
            var text = GetText(testFile);
            var tree = CSharpSyntaxTree.ParseText(text);
            var root = tree.GetRoot();
            var methods = root.DescendantNodes().OfType<MethodDeclarationSyntax>().ToList();
            Assert.AreEqual(2, methods.Count);
            Assert.AreEqual("MyPublicMethodTest", methods[0].Identifier.ValueText);
        }

        private string GetText(string file)
        {
            using (StreamReader streamReader = new StreamReader(file))
                return streamReader.ReadToEnd();
        }

        private void CleanFolder(string folder)
        {
            DirectoryInfo di = new DirectoryInfo(folder);

            foreach (FileInfo file in di.GetFiles())
            {
                file.Delete(); 
            }
        }

    }
}
