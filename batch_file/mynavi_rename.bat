rem ����̓}�C�i�r�p
rem !���g�����߂̂��܂��Ȃ�
setlocal EnableDelayedExpansion

rem �t�H���_��������
for /D %%A in (jobinfo-*) do (
 set STR=%%A
 set STR2=!STR:~8!
 ren %%A\index.html !STR2!.html
)