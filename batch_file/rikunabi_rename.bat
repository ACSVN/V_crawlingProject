rem これはリクナビ用
rem !を使うためのおまじない
setlocal EnableDelayedExpansion

rem フォルダ名を検索
for %%A in (*.png) do (
 set STR=%%A
 set STR2=!STR:~26,30!
 ren "%%A" !STR2!.png
)
