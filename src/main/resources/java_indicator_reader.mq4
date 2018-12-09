int iVAL;
int i_Minute_Prev;
int init(){
i_Minute_Prev =99;
}
int start()
{
int i_Minute = TimeMinute(TimeCurrent());
if (i_Minute != i_Minute_Prev)
   {
   string filename = StringConcatenate("___",Symbol(),Period(),".csv");
   double value =0;
   datetime date;
   string stringdate;
   double number;
   Print("OK!");
   int file = FileOpen(filename,FILE_CSV|FILE_READ);
   if(file !=-1){
   Print("OK!");
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