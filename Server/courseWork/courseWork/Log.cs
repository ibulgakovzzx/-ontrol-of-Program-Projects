using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace courseWork
{
    public class Log
    {
        DateTime timeIn { get; set; }
        DateTime timeOut { get; set; }

        public static void WriteLine(string message)
        {
            using (var writer = new StreamWriter(Path.Combine(Path.GetTempPath(), ".txt")))
            {
                writer.WriteLine(message);
            }
        }
    }
}