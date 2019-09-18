rem これはマイナビ用
rem !を使うためのおまじない
setlocal EnableDelayedExpansion

rem フォルダ名を検索
for /D %%A in (jobinfo-*) do (
 set STR=%%A
 set STR2=!STR:~8!
 ren %%A\index.html !STR2!.html
)