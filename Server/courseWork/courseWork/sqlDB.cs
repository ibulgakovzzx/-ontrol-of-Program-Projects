using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace courseWork
{
    class SQLDB
    {
        OleDbConnection cn;
        string ConnectionString;



        public void cSQL_init(string ConnectionString)
        {
            this.ConnectionString = ConnectionString;
        }


        public void Connect()
        {
            try
            {
                cn = new OleDbConnection();
                cn.ConnectionString = ConnectionString;
                cn.Open();
                Console.WriteLine("Подключение открыто..");
                Console.WriteLine("Свойства подключения:");
                Console.WriteLine("\tСтрока подключения: {0}", cn.ConnectionString);
                Console.WriteLine("\tБаза данных: {0}", cn.Database);
                Console.WriteLine("\tСервер: {0}", cn.DataSource);
                Console.WriteLine("\tВерсия сервера: {0}", cn.ServerVersion);
                Console.WriteLine("\tСостояние: {0}", cn.State);
            }

            catch (Exception exp)
            {
               Console.WriteLine("Can't connect to SQL Server" + exp.Message);
                return;
            }
        }

        public void Disconnect()
        {
            try
            {
                cn.Close();
            }
            catch (Exception exp)
            {
                Console.WriteLine("Can't connect to SQL Server" + exp.Message);
                return;
            }
        }

        public DataTable Query(string SQLstring)
        {
            OleDbCommand cm = null;
            try
            {
                cm = new OleDbCommand(SQLstring, cn);
                OleDbDataAdapter da = new OleDbDataAdapter();

                da.SelectCommand = cm;
                DataTable _table = new DataTable();
                da.Fill(_table);
                da.Dispose();
                cm.Dispose();
                return _table;
            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.Message);
                cm.Dispose();
                return null;
            }

        }

        public int SetCommand(string SQLstring)
        {
            OleDbCommand cm = null;
            int res = -1;
            try
            {
                cm = new OleDbCommand(SQLstring, cn);
                res = cm.ExecuteNonQuery();
                cm.Dispose();
            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.Message);
                cm.Dispose();
                return res;
            }
            return res;

        }
    }
}
