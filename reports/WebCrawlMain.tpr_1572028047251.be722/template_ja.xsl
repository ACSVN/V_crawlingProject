<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet
version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" >
    <xsl:output method="html" encoding="UTF-8"/>
    <xsl:template match="/testsuite">
        <html>
            <head>
                <meta http-equiv="content-type" content="text/html; charset=UTF-8">
                </meta>
                <title>
                    <xsl:value-of select="properties/property[@name='_PRODUCT_NAME']/@value"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="properties/property[@name='_PRODUCT_VERSION_SHORT']/@value"/> テストレポート
                </title>
            </head>
            <body style="font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000">
<!-- START: Top bar -->
                <table background="header_pad.png" border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tbody>
                        <tr height="65">
                            <td align="left"><a href="http://www.t-plan.com"><img src="header_left.png" border="0" height="65" width="520"/></a></td>
                            <td align="right"><a href="http://www.t-plan.com/robot"><img src="header_right.png" border="0" height="65" width="160"/></a></td>
                        </tr>
                    </tbody>
                </table>
<!-- END: Top bar -->
<!-- START: Basic data (server, test script, ...) -->
                <br/>
                <table cellpadding="2" cellspacing="2" border="0"
 style="text-align: left; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000">
                    <tbody>
                        <xsl:if test="properties/property[@name='_URL']">
                            <tr>
                                <td style="vertical-align: top;">
                                    <span style="font-weight: bold;">サーバー:</span>
                                </td>
                                <td style="vertical-align: top;">
                                    <xsl:value-of select="properties/property[@name='_URL']/@value"/>
                                </td>
                            </tr>
                        </xsl:if>
                        <tr>
                            <xsl:variable name="attachScript" select="properties/property[@name='XSL_CONFIG_ATTACH_SCRIPTS']/@value"/>
                            <td style="vertical-align: top;">
                                <span style="font-weight: bold;">テストスクリプト:</span>
                            </td>
                            <td style="vertical-align: top;">
                                <xsl:choose>
                                    <xsl:when test="$attachScript = 'true'">
                                        <a>
                                            <xsl:attribute name="href"><xsl:value-of select="properties/property[@name='_FILENAME']/@value"/>.html</xsl:attribute>
                                            <xsl:value-of select="properties/property[@name='_FILE']/@value"/>
                                        </a>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="properties/property[@name='_FILE']/@value"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </td>
                        </tr>
                        <xsl:if test="properties/property[@name='_REPORT_DESC']">
                            <tr>
                                <td style="vertical-align: top;">
                                    <span style="font-weight: bold;">説明:</span>
                                </td>
                                <td style="vertical-align: top;">
                                    <xsl:call-template name="newline">
                                    	<xsl:with-param name="text" select="properties/property[@name='_REPORT_DESC']/@value"/>
				    </xsl:call-template>
                                </td>
                            </tr>
                        </xsl:if>
                        <tr>
                            <td style="vertical-align: top;">
                                <span style="font-weight: bold;">ステータス:</span>
                            </td>
                            <td style="vertical-align: top;">
                                <xsl:variable name="status" select="properties/property[@name='XSL_PARAM_STATUS']/@value"/>
                                <xsl:choose>
                                    <xsl:when test="$status = '0'">
                                        <font color="magenta">
                                            <b>実行中</b>
                                        </font>
                                        <xsl:if test="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']">
                                           &#160;(参照: <a><xsl:attribute name="href"><xsl:value-of select="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']/@value"/></xsl:attribute>現在の状態</a>)
                                        </xsl:if>
                                    </xsl:when>
                                    <xsl:when test="$status = '1'">
                                        <font color="orange">
                                            <b>一時停止</b>
                                        </font>
                                        <xsl:if test="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']">
                                           &#160;(参照: <a><xsl:attribute name="href"><xsl:value-of select="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']/@value"/></xsl:attribute>現在の状態</a>)
                                        </xsl:if>
                                    </xsl:when>
                                    <xsl:when test="$status = '2'">
                                        <font color="red">
                                            <b>停止</b>
                                        </font>
                                        <xsl:if test="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']">
                                           &#160;(参照: <a><xsl:attribute name="href"><xsl:value-of select="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']/@value"/></xsl:attribute>終了状態</a>)
                                        </xsl:if>
                                    </xsl:when>
                                    <xsl:when test="$status = '3'">

                                        <xsl:choose>
                                            <xsl:when test="properties/property[@name='_EXIT_FILE']">
                                                <b><font color="red">失敗箇所</font>&#160;<a><xsl:attribute name="href"><xsl:value-of select="properties/property[@name='_FILENAME']/@value"/>.html#line<xsl:value-of select="properties/property[@name='_EXIT_LINE']/@value"/></xsl:attribute><xsl:value-of select="properties/property[@name='_EXIT_FILE']/@value"/> 行 <xsl:value-of select="properties/property[@name='_EXIT_LINE']/@value"/></a></b>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <b><font color="red">失敗</font></b>
                                            </xsl:otherwise>
                                        </xsl:choose>

                                        <xsl:if test="properties/property[@name='_EXIT_DESC']">
                                            - <xsl:value-of select="properties/property[@name='_EXIT_DESC']/@value"/>
                                        </xsl:if>
                                        (終了コード =
                                        <xsl:choose>
                                            <xsl:when test="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']">
                                                <xsl:value-of select="properties/property[@name='_EXIT_CODE']/@value"/>, 参照: <a><xsl:attribute name="href"><xsl:value-of select="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']/@value"/></xsl:attribute>現在の状態</a>)
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="properties/property[@name='_EXIT_CODE']/@value"/>)
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:when>
                                    <xsl:when test="$status = '4'">
                                        <font color="green">
                                            <b>完了</b>
                                        </font>
                                        <xsl:if test="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']">
                                           &#160;(参照 <a><xsl:attribute name="href"><xsl:value-of select="properties/property[@name='_REPORT_STATUS_IMAGE_NAME']/@value"/></xsl:attribute>終了状態</a>)
                                        </xsl:if>
                                    </xsl:when>
                                </xsl:choose>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top;">
                                <span style="font-weight: bold;">実行ログ:</span>
                            </td>
                            <td style="vertical-align: top;">
                                <a><xsl:attribute name="href"><xsl:value-of select="properties/property[@name='XSL_PARAM_LOG_FILE']/@value"/></xsl:attribute><xsl:value-of select="properties/property[@name='XSL_PARAM_LOG_FILE']/@value"/></a>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top;">
                                <span style="font-weight: bold;">実行時間:</span>
                            </td>
                            <td style="vertical-align: top;">
                                <xsl:value-of select="properties/property[@name='XSL_PARAM_DURATION_STRING']/@value"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
<!--END: Basic data -->
<!--START: Report body with tables and screenshots -->
<!--START: Table of comments -->
                <xsl:if test="properties/property[@name='XSL_CONFIG_CREATE_COMMENT_TABLE']/@value = 'true'">
                    <xsl:if test="/testsuite/comment | /testsuite/testcase/comment">
                        <xsl:if test="/testsuite/comment[@location = 'top'] | /testsuite/testcase/comment[@location = 'top']">
                            <p>
                                <b>スクリプト説明:</b><br/>
                                <xsl:for-each select="//comment[@location = 'top']">
                                     <xsl:call-template name="newline">
                                    	<xsl:with-param name="text" select="@text"/>
				    </xsl:call-template>&#160;
                                </xsl:for-each>
                            </p>
                        </xsl:if>
                        <xsl:if test="/testsuite/comment[@location != 'top'] | /testsuite/testcase/comment[@location != 'top']">
                            <b>コメント化されたステップ:</b><br/>
                            <table cellpadding="2" cellspacing="1" border="0" style="text-align: left; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000">
                                <tbody>
                                    <xsl:for-each select="//comment[@location != 'top']">
                                        <xsl:variable name="i" select="position()"/>
                                        <tr>
                                            <td><xsl:value-of select="$i"/>.</td>
                                            <td><xsl:call-template name="newline"><xsl:with-param name="text" select="@text"/></xsl:call-template></td>
                                        </tr>
                                    </xsl:for-each>
                                </tbody>
                            </table>
                        </xsl:if>
                        <br/>
                    </xsl:if>
                </xsl:if>
<!--END: Table of comments -->
<!--START: Table of warnings  -->
                <xsl:if test="properties/property[@name='XSL_CONFIG_CREATE_WARNING_TABLE']/@value = 'true'">
                    <xsl:if test="/testsuite/log | /testsuite/testcase/log">
                        <p>
                            <b>警告一覧</b>
                            <table cellpadding="2" cellspacing="1" border="0" bgcolor="black" style="text-align: left; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000">
                                <tbody>
                                    <tr>
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153);">
                                            <b>No.</b>
                                        </td>
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                            <b>イメージ</b>
                                        </td>
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                            <b>説明</b>
                                        </td>
                                    </tr>
                                    <xsl:for-each select="//log">
                                        <xsl:if test="@type = 12"> <!--We are listing just warnings which are logs with type code of 12 -->
                                            <tr>
                                                <td bgcolor="white">
                                                    <center>
                                                        <a>
                                                            <xsl:attribute name="href">#warn<xsl:value-of select="@index"/></xsl:attribute>
                                                            <xsl:value-of select="@index"/>.
                                                        </a>
                                                    </center>
                                                </td>
                                                <td bgcolor="white">
                                                    <xsl:if test="@image">
                                                        <a>
                                                            <xsl:attribute name="href">#<xsl:value-of select="@image"/></xsl:attribute>
                                                            <xsl:value-of select="@image"/>
                                                        </a>
                                                    </xsl:if>
                                                </td>
                                                <td bgcolor="white">
                                                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@desc"/></xsl:call-template>
                                                </td>
                                            </tr>
                                        </xsl:if>
                                    </xsl:for-each>
                                </tbody>
                            </table>
                        </p>
                        <br/>
                    </xsl:if>
                </xsl:if>
<!--END: Table of warnings -->
<!--START: Table of image comparisons  -->
                <xsl:if test="properties/property[@name='XSL_CONFIG_CREATE_COMPARISON_TABLE']/@value = 'true'">
                    <xsl:variable name="failonly" select="properties/property[@name='XSL_CONFIG_DISPLAY_FAILED_COMPARISONS_ONLY']/@value"/>
                    <xsl:if test="($failonly = 'false' and /testsuite/screenshot | /testsuite/testcase/screenshot) or ($failonly = 'true' and /testsuite/screenshot[@cmppassed='false'] | /testsuite/testcase/screenshot[@cmppassed='false'])">
                        <p>
                            <b>イメージ比較一覧</b>
                            <table cellpadding="2" cellspacing="1" border="0" bgcolor="black" style="text-align: left; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000">
                                <tbody>
                                    <tr>
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153);">
                                            <b>イメージ No.</b>
                                        </td>
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                            <b>比較結果</b>
                                        </td>
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                            <b>イメージ名</b>
                                        </td>
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                            <b>テンプレート名</b>
                                        </td>
                                    </tr>
                                    <xsl:for-each select="//screenshot">
                                        <xsl:if test="@cmpresult >= 0 and (@cmppassed = 'false' or $failonly = 'false')"> <!--We are listing just screenshots with cmpresult >= 0 -->
                                            <tr>
                                                <td bgcolor="white">
                                                    <center>
                                                        <a><xsl:attribute name="href">#<xsl:value-of select="@name"/></xsl:attribute><xsl:value-of select="@index"/>.</a>
                                                    </center>
                                                </td>
                                                <td bgcolor="white">
                                                    <xsl:choose>
                                                        <xsl:when test="@cmppassed = 'true'">
                                                            <font color="green">
                                                                <b>合格</b>
                                                            </font> (結果の
                                                            <xsl:value-of select="@cmpresult"/>%は合格基準を満たしています。合格基準:
                                                            <xsl:value-of select="@cmppassrate"/>%
                                                            <xsl:if test="@cmpindex >= 0"> テンプレート
                                                                <xsl:variable name="i" select="@cmpindex"/>
                                                                <xsl:variable name="name" select="template[@index=$i]/@name"/>
                                                                <a><xsl:attribute name="href"><xsl:value-of select="$name"/></xsl:attribute>
                                                                    <xsl:value-of select="$name"/>
                                                                </a>
                                                            </xsl:if>)
                                                        </xsl:when>
                                                        <xsl:when test="@cmppassed = 'false'">
                                                            <font color="red">
                                                                <b>不合格</b>
                                                            </font> (結果の
                                                            <xsl:value-of select="@cmpresult"/>%は合格基準を下回っています。合格基準:
                                                            <xsl:value-of select="@cmppassrate"/>%)
                                                        </xsl:when>
                                                    </xsl:choose>
                                                </td>
                                                <td bgcolor="white">
                                                    <a>
                                                        <xsl:attribute name="href"><xsl:value-of select="@name"/></xsl:attribute>
                                                        <xsl:value-of select="@name"/>
                                                    </a>
                                                </td>
                                                <td bgcolor="white">
                                                    <xsl:if test="./template">
                                                        <xsl:for-each select="./template">
                                                            <a>
                                                                <xsl:attribute name="href"><xsl:value-of select="@name"/></xsl:attribute>
                                                                <xsl:value-of select="@name"/>
                                                            </a>
                                                            <xsl:text> </xsl:text>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                </td>
                                            </tr>
                                        </xsl:if>
                                    </xsl:for-each>
                                </tbody>
                            </table>
                        </p>
                    </xsl:if>
                </xsl:if>
<!--END: Table of image comparisons -->
<!--START: Table of timers and performance testing data -->
                <xsl:if test="//timer">
                    <p>
                        <b>タイマー一覧</b>
                        <table cellpadding="2" cellspacing="1" border="0" bgcolor="black" style="text-align: left; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000">
                            <tbody>
                                <tr>
                                    <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153);">
                                        <b>番号</b>
                                    </td>
                                    <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153);">
                                        <b>タイマー名</b>
                                    </td>
                                    <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                        <b>説明</b>
                                    </td>
                                    <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                        <b>値</b>
                                    </td>
                                    <xsl:if test="/testsuite/timer/@refvalue">
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                            <b>参照値</b>
                                        </td>
                                        <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                            <b>変更</b>
                                        </td>
                                    </xsl:if>
                                </tr>
                                <xsl:for-each select="//timer">
                                    <tr>
                                        <td bgcolor="white">
                                            <center>
                                                <xsl:value-of select="@index"/>.
                                            </center>
                                        </td>
                                        <td bgcolor="white">
                                            <xsl:call-template name="newline"><xsl:with-param name="text" select="@name"/></xsl:call-template>
                                        </td>
                                        <td bgcolor="white">
                                            <xsl:call-template name="newline"><xsl:with-param name="text" select="@desc"/></xsl:call-template>
                                        </td>
                                        <td bgcolor="white">
                                            <xsl:choose>
                                                <xsl:when test="@reporttime">
                                                    <xsl:value-of select="@reporttime"/>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <xsl:value-of select="@displaytime"/> (<xsl:value-of select="@time"/>ミリ秒)
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </td>
                                    <xsl:if test="/testsuite/timer/@refvalue">
                                        <td bgcolor="white">
                                            <xsl:if test="@refdisplaytime">
                                                <xsl:choose>
                                                    <xsl:when test="@refreporttime">
                                                        <xsl:value-of select="@refreporttime"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="@refdisplaytime"/> (<xsl:value-of select="@refvalue"/>ミリ秒)
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:if>
                                        </td>
                                        <td bgcolor="white">
                                            <xsl:if test="@percentchange">
                                                <center>
                                                    <xsl:variable name="a" select="@refvalue"/>
                                                    <xsl:variable name="b" select="@time"/>
                                                    <xsl:choose>
                                                        <xsl:when test="$a &lt; $b">
                                                            <font color="red"><xsl:value-of select="@percentchange"/>%</font>
                                                        </xsl:when>
                                                        <xsl:when test="$a &gt; $b">
                                                            <font color="green"><xsl:value-of select="@percentchange"/>%</font>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:value-of select="@percentchange"/>%
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </center>
                                            </xsl:if>
                                        </td>
                                    </xsl:if>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </p>
                </xsl:if>
<!--END: Table of timers and performance testing data -->
<!--We just apply various node specific templates specified further on. -->
                <xsl:apply-templates/>
            </body>
        </html>
        <xsl:comment>NOTE: these invisible values are intended to provide info about this report to external applications.</xsl:comment>
<!-- version=2.0.Beta-20090817.1 -->
        <xsl:comment>version=
            <xsl:value-of select="properties/property[@name='_PRODUCT_VERSION_LONG']/@value"/>
        </xsl:comment>
<!-- running=false stopped=false paused=false exitCode=0 -->
        <xsl:variable name="status2" select="properties/property[@name='XSL_PARAM_STATUS']/@value"/>
        <xsl:choose>
            <xsl:when test="$status2 = '0'"><!-- Running -->
                <xsl:comment>running=true</xsl:comment>
                <xsl:comment>stopped=false</xsl:comment>
                <xsl:comment>paused=false</xsl:comment>
            </xsl:when>
            <xsl:when test="$status2 = '1'"><!-- Paused -->
                <xsl:comment>running=true</xsl:comment>
                <xsl:comment>stopped=false</xsl:comment>
                <xsl:comment>paused=true</xsl:comment>
            </xsl:when>
            <xsl:when test="$status2 = '2'"><!-- Stopped -->
                <xsl:comment>running=false</xsl:comment>
                <xsl:comment>stopped=true</xsl:comment>
                <xsl:comment>paused=false</xsl:comment>
            </xsl:when>
            <xsl:otherwise><!-- Failed or success -->
                <xsl:comment>running=false</xsl:comment>
                <xsl:comment>stopped=false</xsl:comment>
                <xsl:comment>paused=false</xsl:comment>
                <xsl:comment>exitCode=
                    <xsl:value-of select="//properties/property[@name='_EXIT_CODE']/@value"/>
                </xsl:comment>
            </xsl:otherwise>
        </xsl:choose>
<!-- imageCount=6 -->
        <xsl:comment>imageCount=
            <xsl:value-of select="count(//screenshot)"/>
        </xsl:comment>
<!-- failedComparisons=0 -->
        <xsl:comment>failedComparisons=
            <xsl:value-of select="count(//screenshot[@cmppassed='false'])"/>
        </xsl:comment>
<!-- warningCount=3 -->
        <xsl:comment>warningCount=
            <xsl:value-of select="count(//log[@type='110'])"/>
        </xsl:comment>
<!-- executionTimeInSec=6 -->
        <xsl:comment>executionTimeInSec=
            <xsl:value-of select="//properties/property[@name='XSL_PARAM_DURATION_SECONDS']/@value"/>
        </xsl:comment>
    </xsl:template>
<!--END: Report body -->
<!--START: Screenshot template -->
    <xsl:template match="screenshot">
        <p>
<!--Screenshot anchor -->
            <a>
                <xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
            </a>
<!--Screenshot index, link and description-->
            <xsl:value-of select="@index"/>.
            <xsl:text> </xsl:text>
            <a>
                <xsl:choose>
                    <xsl:when test="@relpath">
                        <xsl:attribute name="href"><xsl:value-of select="@relpath"/></xsl:attribute>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="href"><xsl:value-of select="@name"/></xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:value-of select="@name"/>
            </a>
            <xsl:if test="@desc">-
                <xsl:call-template name="newline"><xsl:with-param name="text" select="@desc"/></xsl:call-template>
            </xsl:if>
            <br/>
<!--Link to the test script and line number -->
            <i>
                <font size="-1">- スクリプト
                    <xsl:choose>
                        <xsl:when test="//properties/property[@name='XSL_CONFIG_ATTACH_SCRIPTS']/@value = 'true'">
                            <a>
                                <xsl:attribute name="href"><xsl:value-of select="@scriptname"/>.html#line<xsl:value-of select="@line + 1"/></xsl:attribute>
                                <xsl:value-of select="@script"/>
                            </a>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="@script"/>
                        </xsl:otherwise>
                    </xsl:choose><xsl:if test="@line">, 行
                    <xsl:value-of select="@line + 1"/></xsl:if>
<!-- Attachments -->
                    <xsl:if test="./attach">
                        <br/>- 添付:
                        <xsl:apply-templates/>
                    </xsl:if>
<!-- Comparison results -->
                    <xsl:if test="@cmpresult >= 0">
                        <br/> - 比較結果:
                        <xsl:text> </xsl:text>
                        <xsl:choose>
                            <xsl:when test="@cmpresult >= @cmppassrate">
                                <font color="green">
                                    <b>合格</b>
                                </font> (結果の
                                <xsl:value-of select="@cmpresult"/>%は合格基準を満たしています。合格基準:
                                <xsl:value-of select="@cmppassrate"/>%
                                <xsl:if test="@cmpindex >= 0"> テンプレート
                                    <xsl:variable name="i" select="@cmpindex"/>
                                    <xsl:variable name="name" select="template[@index=$i]/@name"/>
                                    <a>
                                        <xsl:attribute name="href"><xsl:value-of select="$name"/></xsl:attribute>
                                        <xsl:value-of select="$name"/>
                                    </a>
                                </xsl:if>)
                            </xsl:when>
                            <xsl:otherwise>
                                <font color="red">
                                    <b>不合格</b>
                                </font> (結果の
                                <xsl:value-of select="@cmpresult"/>%は合格基準を下回っています。合格基準:
                                <xsl:value-of select="@cmppassrate"/>%)
                            </xsl:otherwise>
                        </xsl:choose>
  <!--
    We have to test existence of template nodes because users may plug in image comparison
    algorithms which just analyze the desktop image and do not use any template.
  -->
                        <xsl:if test="./template">. テンプレート:
                            <xsl:for-each select="./template">
                                <a>
                                    <xsl:attribute name="href"><xsl:value-of select="@name"/></xsl:attribute>
                                    <xsl:value-of select="@name"/>
                                </a>
                                <xsl:text> </xsl:text>
                            </xsl:for-each>
                        </xsl:if>
                    </xsl:if>
                </font>
            </i>
            <br/>
            <xsl:variable name="scrname" select="@name"/>
            <xsl:if test="//property[@name='XSL_CONFIG_DISPLAY_WARNINGS']/@value = 'true'">
                <xsl:for-each select="//log[@type='110' and @image = $scrname]">
                    <a>
                        <xsl:attribute name="name">warn<xsl:value-of select="@index"/></xsl:attribute>
                    </a>
                    <font color="red">
                        <b>警告: </b>
                    </font>
                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@desc"/></xsl:call-template>
                    <i> (<xsl:value-of select="@date"/>)
                    </i>
                    <br/>
                    <br/>
                </xsl:for-each>
            </xsl:if>
            <img>
                <xsl:choose>
                    <xsl:when test="@relpath">
                        <xsl:attribute name="src"><xsl:value-of select="@relpath"/></xsl:attribute>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="src"><xsl:value-of select="@name"/></xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:attribute name="alt"><xsl:value-of select="@desc"/></xsl:attribute>
                <xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute>
                <xsl:attribute name="height"><xsl:value-of select="@height"/></xsl:attribute>
            </img>
        </p>
    </xsl:template>
<!--END: Screenshot template -->
<!--START: Attachment template -->
    <xsl:template match="attach">
        <a>
            <xsl:attribute name="href"><xsl:value-of select="@file"/></xsl:attribute>
            <xsl:value-of select="@file"/>
        </a>
        <xsl:text> </xsl:text>
    </xsl:template>
<!--END: Attachment template -->
<!--START: Warning template -->
    <xsl:template match="log">
        <xsl:choose>
            <xsl:when test="@type = 12">  <!-- Warning -->
                <xsl:if test="//property[@name='XSL_CONFIG_DISPLAY_WARNINGS']/@value = 'true'">
                    <a>
                        <xsl:attribute name="name">warn<xsl:value-of select="@index"/></xsl:attribute>
                    </a>
                    <font color="red">
                        <b>警告: </b>
                    </font>
                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@desc"/></xsl:call-template>
                    <i> (<xsl:value-of select="@date"/>)
                    </i>
                    <br/>
                </xsl:if>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
<!--END: Warning template -->
<!--START: Step template -->
    <xsl:template match="step">
        <xsl:choose>
          <xsl:when test="//property[@name='XSL_CONFIG_INCLUDE_STEPS_IN_BODY']/@value = 'false'"></xsl:when>
          <xsl:otherwise>
            <p>
                <a>
                <xsl:attribute name="name">step<xsl:value-of select="../@number"/>_<xsl:value-of select="@index"/></xsl:attribute>
                </a>
                <b>ステップ
                    <xsl:value-of select="@index"/>:
                </b>
                <i>"
                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@name"/></xsl:call-template>"
                </i> (<xsl:choose>
                    <xsl:when test="@result = 'pass'">
                        <font color="green">テスト済み &amp; 合格</font>
                    </xsl:when>
                    <xsl:when test="@result = 'fail'">
                        <font color="red">テスト済み &amp; 不合格</font>
                    </xsl:when>
                    <xsl:when test="@result = 'nt'">
                        <font color="black">未テスト</font>
                    </xsl:when>
                    <xsl:when test="@result = 'na'">
                        <font color="blue">利用不可</font>
                    </xsl:when>
                </xsl:choose>)
                <xsl:if test="@instruct | @expected | @actual | @notes">
                  <table border="0">
                    <xsl:if test="@instruct">
                        <tr>
                            <td>
                                <font size="-1">
                                    <b>指示:</b>
                                </font>
                            </td>
                            <td>
                                <font size="-1">
                                    <xsl:call-template name="newline">
                                    	<xsl:with-param name="text" select="@instruct"/>
				    </xsl:call-template>
                                </font>
                            </td>
                        </tr>
                    </xsl:if>
                    <xsl:if test="@expected">
                        <tr>
                            <td>
                                <font size="-1">
                                    <b>期待した結果:</b>
                                </font>
                            </td>
                            <td>
                                <font size="-1">
                                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@expected"/></xsl:call-template>
                                </font>
                            </td>
                        </tr>
                    </xsl:if>
                    <xsl:if test="@actual">
                        <tr>
                            <td>
                                <font size="-1">
                                    <b>実際の結果:</b>
                                </font>
                            </td>
                            <td>
                                <font size="-1">
                                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@actual"/></xsl:call-template>
                                </font>
                            </td>
                        </tr>
                    </xsl:if>
                    <xsl:if test="@notes">
                        <tr>
                            <td>
                                <font size="-1">
                                    <b>メモ:</b>
                                </font>
                            </td>
                            <td>
                                <font size="-1">
                                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@notes"/></xsl:call-template>
                                </font>
                            </td>
                        </tr>
                    </xsl:if>
                </table>
            </xsl:if>
          </p>
          <hr width="50%" align="left"/>
          </xsl:otherwise>
       </xsl:choose>
    </xsl:template>
<!--END: Step template -->
<!--START: Result template -->
    <xsl:template match="result">
        <xsl:if test="failed">
            <p/>
            <hr/>
            <h3>
                <font color="#FF3333">失敗の詳細</font>
            </h3>
        <p/>
            次の終了コードで失敗しました: <xsl:value-of select="code"/> 、後続の失敗: <b>
                <xsl:value-of select="failed/@cname"/>
            </b> イメージ比較、場所: <code>
                <xsl:choose>
                    <xsl:when test="failed/@script">
                        <xsl:value-of select="failed/@script"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="script"/>
                    </xsl:otherwise>
                </xsl:choose>
            </code> 行 <code>
                <xsl:choose>
                    <xsl:when test="failed/@line">
                        <xsl:value-of select="failed/@line"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="line"/>
                    </xsl:otherwise>
                </xsl:choose>
            </code><xsl:if test="desc">, 提示された理由: <ul><code><xsl:value-of select="desc"/></code></ul>
    	    </xsl:if>
    	</xsl:if>
    	<xsl:apply-templates select="failed"/>
    	<xsl:apply-templates select="exitscreen"/>
    </xsl:template>
    
    <xsl:template match="failed">
       <xsl:if test="@command">
       <p><u>失敗コマンド:</u>
       <ul><code><xsl:value-of select="@command"/></code></ul>
       </p>
       </xsl:if>
       <xsl:if test="template">
       <p><u>失敗テンプレートイメージ:</u></p>
       <ul>
          <table border="0" spacing="10">
    	     <xsl:apply-templates select="template"/>
    	  </table>
       </ul>
       </xsl:if>
    </xsl:template>
   
    <xsl:template match="template">
        <tr>
            <td align="center" valign="middle">
                <a target="_blank">
                        <xsl:attribute name="href">
                            <xsl:value-of select="@link"/>
                        </xsl:attribute>            
                    <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="@link"/>
                        </xsl:attribute>            
                        <xsl:attribute name="alt">
                            <xsl:value-of select="current()"/>
                        </xsl:attribute>
                        <xsl:choose>
                            <xsl:when test="@swidth">
                                <xsl:attribute name="width">
                                    <xsl:value-of select="@swidth"/>
                                </xsl:attribute>
                                <xsl:attribute name="height">
                                    <xsl:value-of select="@sheight"/>
                                </xsl:attribute>
                            </xsl:when>
                            <xsl:when test="@width">
                                <xsl:attribute name="width">
                                    <xsl:value-of select="@width"/>
                                </xsl:attribute>
                                <xsl:attribute name="height">
                                    <xsl:value-of select="@height"/>
                                </xsl:attribute>
                            </xsl:when>
                        </xsl:choose>
                    </img>
                </a>
            </td>
            <td style="font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000" align="left" valign="middle">
                <xsl:value-of select="@link"/>
                <xsl:if test="@width"> (<xsl:value-of select="@width"/>x<xsl:value-of select="@height"/>)</xsl:if>
            </td>
        </tr>
    </xsl:template>
    
    <xsl:template match="exitscreen">
       <p><u>終了スクリーンショット:</u></p>
       <p>
           <table width="100%" border="0" cellpadding="5">
               <tr>
                   <td bgcolor="FFDDDD" style="font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000">
                       <b>ヒント:</b> スクリーンショットに接続するには、<b><i>静的イメージ</i></b>接続を使い、パラメータの調整あるいは新しくテンプレートイメージを切り取ってください。
                   </td>
               </tr>
           </table>
       </p>
       <ul>
        <img>
            <xsl:attribute name="src"><xsl:value-of select="@link"/></xsl:attribute>
            <xsl:attribute name="alt"><xsl:value-of select="current()"/></xsl:attribute>
        </img>
       </ul>
    </xsl:template>
    
<!--START: Test Case template -->
<!--END: Step template -->
<!--START: Test Case template -->
    <xsl:template match="testcase">
        <p>
            <b>テストケース
                <xsl:value-of select="@index"/> (T-Plan No.
                <xsl:value-of select="@number"/>)
                <xsl:if test="@name">:
                    <i>"<xsl:call-template name="newline"><xsl:with-param name="text" select="@name"/></xsl:call-template>"</i>
                </xsl:if>
            </b>
            <hr/>
        </p>
        <xsl:if test="step">
            <p>
                <b>テストステップサマリー</b>
                <table cellpadding="2" cellspacing="1" border="0" bgcolor="black" style="text-align: left; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#000000">
                    <tbody>
                        <tr>
                            <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153);">
                                <b>No.</b>
                            </td>
                            <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                <b>名前</b>
                            </td>
                            <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                <b>ステータス</b>
                            </td>
                            <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                <b>指示</b>
                            </td>
                            <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                <b>期待</b>
                            </td>
                            <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                <b>実際</b>
                            </td>
                            <td style="vertical-align: top; color: rgb(255, 255, 255); background-color: rgb(153, 153, 153)">
                                <b>メモ</b>
                            </td>
                        </tr>
                        <xsl:for-each select="step">
                            <tr>
                                <td bgcolor="white">
                                    <xsl:value-of select="@index"/>
                                </td>
                                <td bgcolor="white">
                                    <a>
                                        <xsl:attribute name="href">#step<xsl:value-of select="../@number"/>_<xsl:value-of select="@index"/></xsl:attribute>
                                        <xsl:call-template name="newline"><xsl:with-param name="text" select="@name"/></xsl:call-template>
                                    </a>
                                </td>
                                <td bgcolor="white">
                                    <xsl:choose>
                                        <xsl:when test="@result = 'pass'">
                                            <font color="green">テスト済み &amp; 成功</font>
                                        </xsl:when>
                                        <xsl:when test="@result = 'fail'">
                                            <font color="red">テスト済み &amp; 失敗</font>
                                        </xsl:when>
                                        <xsl:when test="@result = 'nt'">
                                            <font color="black">未テスト</font>
                                        </xsl:when>
                                        <xsl:when test="@result = 'na'">
                                            <font color="blue">利用不可</font>
                                        </xsl:when>
                                    </xsl:choose>
                                </td>
                                <td bgcolor="white">
                                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@instruct"/></xsl:call-template>
                                </td>
                                <td bgcolor="white">
                                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@expected"/></xsl:call-template>
                                </td>
                                <td bgcolor="white">
                                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@actual"/></xsl:call-template>
                                </td>
                                <td bgcolor="white">
                                    <xsl:call-template name="newline"><xsl:with-param name="text" select="@notes"/></xsl:call-template>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </p><br/>
        </xsl:if>
        <xsl:apply-templates/>
    </xsl:template>
<!--END: Test Case template -->
<!--START: Template to replace new lines with <br> -->
<xsl:template name="newline">
    <xsl:param name="text"/>
    <xsl:choose>
        <xsl:when test="contains($text,'&#10;')">
            <xsl:value-of select="substring-before($text,'&#10;')"/>
            <br/>
            <xsl:call-template name="newline">
                <xsl:with-param name="text" select="substring-after($text,'&#10;')"/>
            </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$text"/>
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>
<!--END: new line template -->
</xsl:stylesheet>