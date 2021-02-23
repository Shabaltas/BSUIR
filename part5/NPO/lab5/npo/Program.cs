using System;
using System.Threading;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;

namespace npo
{
    internal class Program
    {
        
        public static void Main(string[] args)
        {
            IWebDriver wdriver = new ChromeDriver(@"C:\Users\Asus\Desktop");

            IWebElement elem, buttonElem, inputElem, listElement;
            wdriver.Url = "https://120minsk.schools.by/";
            wdriver.Manage().Window.Maximize();              
            elem = wdriver.FindElement(By.Id("sch_login_lnk"));
            elem.Click();
            inputElem = wdriver.FindElement(By.XPath("//input[@name='username']"));
            Thread.Sleep(1000);
            inputElem.SendKeys("");
            listElement = wdriver.FindElement(By.XPath("//input[@name='password']"));
            Thread.Sleep(1000);
            listElement.SendKeys("1111");

            buttonElem = wdriver.FindElement(By.XPath("//input[@value='Войти']"));
            Thread.Sleep(1000);
            buttonElem.Click();

            var actual = wdriver.FindElement(By.XPath("//li[contains(.,'Это поле обязательно.')]"));
            if (actual.Text.Equals("Это поле обязательно."))
            {
                Console.WriteLine("Test completed");
            }
            else
            {
                Console.WriteLine("Test failed");
            }

            wdriver.Close();


        }
    }
}