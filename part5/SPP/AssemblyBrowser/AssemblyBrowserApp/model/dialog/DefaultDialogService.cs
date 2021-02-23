using Microsoft.Win32;
using System.Windows;

namespace AssemblyBrowserApp.model.dialog
{
    public class DefaultDialogService : IDialogService
    {
        public string FilePath { get; set; }

        public bool OpenFileDialog()
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = @"Assemblies|*.dll;*.exe";
            openFileDialog.Title = @"Select assembly";
            openFileDialog.Multiselect = false;
            if (openFileDialog.ShowDialog() == true)
            {
                FilePath = openFileDialog.FileName;
                return true;
            }
            return false;
        }

        public void ShowMessage(string message)
        {
            MessageBox.Show(message);
        }
    }
}
