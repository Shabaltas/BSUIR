using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Input;

namespace AssemblyBrowser
{
    public class OpenCommand : ICommand
    {
        public event EventHandler CanExecuteChanged
        {
            add => CommandManager.RequerySuggested += value;
            remove => CommandManager.RequerySuggested -= value;
        }

        public OpenCommand(Action execute, Func<object, bool> canExecute = null)
        {
            _execute = execute;
            _canExecute = canExecute;
        }

        private Action _execute;
        private Func<object, bool> _canExecute;

        public bool CanExecute(object parameter)
        {
            return _canExecute == null || CanExecute(parameter);
        }

        public void Execute(object parameter)
        {
            _execute();
        }
    }
}
