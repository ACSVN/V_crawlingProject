rem ����̓��N�i�r�p
rem !���g�����߂̂��܂��Ȃ�
setlocal EnableDelayedExpansion

rem �t�H���_��������
for %%A in (*.png) do (
 set STR=%%A
 set STR2=!STR:~26,30!
 ren "%%A" !STR2!.png
)
