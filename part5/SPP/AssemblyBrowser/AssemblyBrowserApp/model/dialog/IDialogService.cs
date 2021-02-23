using System;
using System.Collections.Generic;
using System.Text;

namespace AssemblyBrowserApp.model.dialog
{
    interface IDialogService
    {
        void ShowMessage(string message);   // показ сообщения
        string FilePath { get; set; }   // путь к выбранному файлу
        bool OpenFileDialog();  // открытие файла
    }
}
