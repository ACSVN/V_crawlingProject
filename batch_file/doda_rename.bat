rem �����doda�p
rem "\doda.jp\DodaFront\View\JobSearchDetail"�̒��ɒu���Ă��������B
rem !���g�����߂̂��܂��Ȃ�
setlocal EnableDelayedExpansion
rem �t�H���_��������
for /D %%A in (j_jid__*) do (
 set STR=%%A
 set STR2=!STR:~7!
 ren %%A\-tab__jd\PC_\guide\index.html !STR2!.html
)

rem �t�H���_��������
for /D %%A in (j_jid__*) do (
 set STR=%%A
 set STR2=!STR:~7!
 ren %%A\-tab__jd\index.html !STR2!.html
)

rem �t�H���_��������
for /D %%A in (j_jid__*) do (
 set STR=%%A
 set STR2=!STR:~7!
 ren %%A\index.html !STR2!.html
)