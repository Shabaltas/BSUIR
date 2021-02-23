using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using System.Collections.ObjectModel;
using AssemblyBrowserApp.model.dialog;
using AssemblyBrowserApp.model.browser;
using AssemblyBrowserLogic.structures;
using System.Windows.Input;


namespace AssemblyBrowser
{
    class ApplicationViewModel : INotifyPropertyChanged
    {
        private IDialogService dialogService;
        private IBrowserService browserService;
        private string _openedFile;
        public List<BaseStructure> Tree { get; set; }
        public string OpenedFile
        {
            get
            {
                return _openedFile;
            }
            set
            {
                _openedFile = value;
                Tree = browserService.OpenAndParse(value);
                OnPropertyChanged(nameof(Tree));
            }
        }
        public ICommand OpenFileCommand
        {
            get
            {
                return new OpenCommand(() =>
                    {
                        Console.WriteLine("hhh" + dialogService);
                      bool isFileOpened = dialogService.OpenFileDialog();
                      if (isFileOpened)
                      {
                          OpenedFile = dialogService.FilePath;
                          OnPropertyChanged(nameof(OpenedFile));
                      }
                    });
            }
        }

        public ApplicationViewModel()
        {
            dialogService = new DefaultDialogService();
            browserService = new BrowserService();
        }
        public event PropertyChangedEventHandler PropertyChanged;
        public void OnPropertyChanged(string propertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
