using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.OleDb;
using System.Data;
using courseWork.Testing;

namespace courseWork
{
    class Program
    {
        private static int PORT = 9998;
        private static IPAddress ipAddress;
        public static SQLDB sqlDB = new SQLDB();
        private static List<Sotrudnik> worker = new List<Sotrudnik>();
        private static TcpListener listener;
        private static URV urv = new URV();
        private static DataTable ds;
        private static Position pos;

        const byte codeNewUser = 49;
        const byte codeChangePosition = 50;
        const byte codeGetStatistick = 51;
        const byte codeRegisterUser = 52;

        static void Main(string[] args)
        {
           /* Sotrudnik sotrudnik0 = null;

            Sotrudnik sotrudink1 = new Sotrudnik();
            sotrudink1.FIO = null;
            sotrudink1.ID = 0;
            sotrudink1.otdel = null;
            sotrudink1.inWork = null;

            Sotrudnik sotrudink2 = new Sotrudnik();
            sotrudink2.FIO = "Test";
            sotrudink2.ID = 0;
            sotrudink2.otdel = new Otdel();
            sotrudink2.inWork = new Position();

            TLog log = new TLog();
            TRPZ rpz = new TRPZ();
            TsqlDB sqlDB = new TsqlDB();
            TURV urv = new TURV();

            log.TWriteLine("test");
            log.TWriteLine(null);

            rpz.TGetDataNachisl(sotrudnik0);
            rpz.TGetDataNachisl(sotrudink1);
            rpz.TGetDataNachisl(sotrudink2);

            sqlDB.TcSQL_init(null);
            sqlDB.TcSQL_init("test");
            sqlDB.TcSQL_init("Provider = Microsoft.Ace.OLEDB.12.0; " + @"Data Source= ../../../worker_time.accdb; Persist Security Info=False;");

            sqlDB.TConnect();

            sqlDB.TQuery(null);
            sqlDB.TQuery("SELECT * FROM Worker");

            sqlDB.TDisconnect();

            urv.TCalcEdetg(sotrudnik0);
            urv.TCalcEdetg(sotrudink1);
            urv.TCalcEdetg(sotrudink2);

            urv.TgetDataRV(sotrudnik0);
            urv.TgetDataRV(sotrudink1);
            urv.TgetDataRV(sotrudink2);*/
            sqlDB.cSQL_init("Provider = Microsoft.Ace.OLEDB.12.0; " + @"Data Source= ../../../worker_time.accdb; Persist Security Info=False;");
            pos = new Position();
            ipAddress = IPAddress.Parse("0.0.0.0");
            listener = new TcpListener(ipAddress, PORT);
            sqlDB.Connect();
            try
            {
                Console.WriteLine($"Ip addres: {ipAddress.ToString()}");
                listener.Start();
                String msg;
                String query;
                while (true)
                {
                    TcpClient client = listener.AcceptTcpClient();
                    NetworkStream stream = client.GetStream();
                    byte[] recBytes = new byte[1024];
                    stream.Read(recBytes, 0, 1024);
                    MessageBox.Show($"Recive bytes length: {recBytes.Length}");
                    switch (recBytes[0])
                    {
                        case codeNewUser:
                            msg = Convert.ToString(recBytes);
                            var msg_arr = msg.Split(',').ToArray();
                            MessageBox.Show("Информационное сообщение", "Добавлен новый пользователь!");
                            //TODO set name and firstname
                            String name = "Petr";
                            String firstName = "Ivanov";
                            query = $"INSERT INTO Worker(Name,Firstname) VALUES('{name}','{firstName}')";
                            ds = sqlDB.Query(query);
                            Console.WriteLine("Added new user");
                            break;
                        case codeChangePosition:
                            msg = Convert.ToString(recBytes);
                            MessageBox.Show(Encoding.ASCII.GetString(recBytes), "Изменилось положение для пользователя!");
                            //TODO set parametrs
                            int workerID = 1;
                            DateTime Date = DateTime.Now;
                            pos.inWork = true;
                            query = $"INSERT INTO WorkTime(WorkerID,Data,Position) VALUES({workerID},'{Date.ToString().Replace('.', '-')}',{pos.inWork})";
                            ds = sqlDB.Query(query);
                            Console.WriteLine("Chabge position from user");
                            break;
                        case codeGetStatistick:
                            //TODO: getStatisctic from db
                            byte[] data = Encoding.UTF8.GetBytes("Message");
                            NetworkStream writeStream = client.GetStream();
                            writeStream.Write(data, 0, data.Length);
                            writeStream.Flush();
                            Console.WriteLine("Send statistic to user");
                            break;
                        case codeRegisterUser:
                            String _name = "Petr";
                            String _firstName = "Ivanov";
                            Sotrudnik sotr = new Sotrudnik();
                            sotr.FIO = _name + _firstName;
                            sotr.ID = worker.Count - 1;
                            worker.Add(sotr);
                            Console.WriteLine("Register new user");
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

        static private string getLocalIPAddress()
        {
            IPHostEntry host;
            string localIP = "";
            host = Dns.GetHostEntry(Dns.GetHostName());
            foreach (IPAddress ip in host.AddressList)
            {
                if (ip.AddressFamily == AddressFamily.InterNetwork)
                {
                    localIP = ip.ToString();
                    break;
                }
            }
            return localIP;
        }


    }
}


