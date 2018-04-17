for /l %%x in (1, 1, 20) do (
   echo %%x
   "bat/run generate.bat" & "bat/run post.bat"
)