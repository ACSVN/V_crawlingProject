rem これはdoda用
rem "\doda.jp\DodaFront\View\JobSearchDetail"の中に置いてください。
rem !を使うためのおまじない
setlocal EnableDelayedExpansion
rem フォルダ名を検索
for /D %%A in (j_jid__*) do (
 set STR=%%A
 set STR2=!STR:~7!
 ren %%A\-tab__jd\PC_\guide\index.html !STR2!.html
)

rem フォルダ名を検索
for /D %%A in (j_jid__*) do (
 set STR=%%A
 set STR2=!STR:~7!
 ren %%A\-tab__jd\index.html !STR2!.html
)

rem フォルダ名を検索
for /D %%A in (j_jid__*) do (
 set STR=%%A
 set STR2=!STR:~7!
 ren %%A\index.html !STR2!.html
)