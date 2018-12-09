int iVAL;
int init()
{
int i_Minute = TimeMinute(TimeCurrent());
int i_Minute_Prev =99;

if (i_Minute != i_Minute_Prev)
{
string filename = StringConcatenate("___",Symbol(),Period(),".csv");
double value =0;
datetime date;
string stringdate;
double number;
int file = FileOpen(filename,FILE_CSV|FILE_READ);
if(file !=-1){

for(int i = ObjectsTotal() -1; i >=0; i--) {
   ObjectDelete(ObjectName(i));
}
   value = FileReadNumber(file);
   ObjectCreate("BreakOut", OBJ_HLINE, 0, Time[0], value, 0, 0);
   Print(value);
   date = FileReadDatetime(file);
   Print(date);
   ObjectCreate("T1",OBJ_VLINE,0,date,0);
   FileClose(file);
   }
}
i_Minute_Prev = i_Minute;
}