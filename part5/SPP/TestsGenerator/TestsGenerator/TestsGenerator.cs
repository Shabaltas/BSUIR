using System.Threading.Tasks;

namespace TestsGenerator
{
    public interface ITestsGenerator
    {
       Task GenerateTests(string[] classFiles, string folderToSave);
    }
}
