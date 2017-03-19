using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace courseWork
{
    class Program
    {
        static int PORT = 9998;
        static IPAddress ipAddress = IPAddress.Parse("0.0.0.0");

        const byte codeNewUser = 49;
        const byte codeChangePosition = 50;
        const byte codeGetStatistick = 51;

        static void Main(string[] args)
        {
            /*IPHostEntry ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
            IPAddress ipAddress = ipHostInfo.AddressList[0];*/
            Console.WriteLine($"Ip addres: {ipAddress.ToString()}");
            IPEndPoint ipEndPoint = new IPEndPoint(ipAddress, PORT);
            TcpListener listener = new TcpListener(ipAddress, PORT);
            try
            {
                listener.Start();
                String msg;
                while (true)
                {
                    TcpClient client = listener.AcceptTcpClient();
                    NetworkStream stream = client.GetStream();
                    byte[] recBytes = new byte[1024];
                    stream.Read(recBytes, 0, 1024);
                    switch (recBytes[0])
                    {
                        case codeNewUser:
                            msg = Convert.ToString(recBytes);
                            MessageBox.Show(msg, "Добавлен новый пользователь!");
                            Console.WriteLine("Added new user");
                            break;
                        case codeChangePosition:
                            msg = Convert.ToString(recBytes);
                            MessageBox.Show(msg, "Изменилось положение для пользователя!");
                            Console.WriteLine("Chabge position from user");
                            break;
                        case codeGetStatistick:
                            //TODO: getStatisctic from db
                            byte[] data = Encoding.UTF8.GetBytes("Message");
                            NetworkStream writeStream = client.GetStream();
                            writeStream.Write(data,0,data.Length);
                            Console.WriteLine("Send statistic to user");
                            break;
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Ошибка сервера");
                Console.ReadKey();
            }
        }

    }
}


