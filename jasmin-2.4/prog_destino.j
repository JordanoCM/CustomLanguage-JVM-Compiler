.source prog_destino.java
.class public prog_destino
.super java/lang/Object
.method public <init>()V
.limit stack 1
.limit locals 1
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
.limit stack 1000
.limit locals 1000
ldc2_w 0.0
dstore 1
ldc2_w 1.0
dstore 3
ldc2_w 2.718281828459045
dstore 5
ldc2_w 1000.0
dstore 7
dload 3
dload 1
dsub
dload 7
ddiv
dstore 9
dload 5
dload 1
invokestatic java/lang/Math/pow(DD)D
dload 1
ldc2_w 2.0
invokestatic java/lang/Math/pow(DD)D
dmul
dload 5
dload 3
invokestatic java/lang/Math/pow(DD)D
dload 3
ldc2_w 2.0
invokestatic java/lang/Math/pow(DD)D
dmul
dadd
dstore 11
ldc2_w 0.0
dstore 13
ldc2_w 1.0
dstore 15
Floop1:
dload 15
dload 7
dcmpg
iflt LcmpTrue1
iconst_0
goto LcmpStore1
LcmpTrue1:
iconst_1
LcmpStore1:
istore 17
iload 17
ifne FTrue1
goto FEnd1
FTrue1:
dload 1
dload 15
dload 9
dmul
dadd
dstore 13
dload 11
ldc2_w 2.0
dload 5
dload 13
invokestatic java/lang/Math/pow(DD)D
dload 13
ldc2_w 2.0
invokestatic java/lang/Math/pow(DD)D
dmul
dmul
dadd
dstore 11
dload 15
ldc2_w 1.0
dadd
dstore 15
goto Floop1
FEnd1:
dload 9
ldc2_w 2.0
ddiv
dload 11
dmul
dstore 19
dload 19
ldc2_w 0.0
dcmpg
ifgt LcmpTrue3
iconst_0
goto LcmpStore3
LcmpTrue3:
iconst_1
LcmpStore3:
istore 23
iload 23
ifne TIFTrue4
ldc "ERRO "
astore 27
aload 27
goto TIFEnd4
TIFTrue4:
ldc "Resultado da integracao "
astore 25
aload 25
TIFEnd4:
astore 21
aload 21
dload 19
invokestatic java/lang/String/valueOf(D)Ljava/lang/String;
invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
astore 29
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 29
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
ldc2_w 0.0
dstore 31
Floop5:
dload 31
ldc2_w 3.0
dcmpg
iflt LcmpTrue5
iconst_0
goto LcmpStore5
LcmpTrue5:
iconst_1
LcmpStore5:
istore 37
iload 37
ifne FTrue5
goto FEnd5
FTrue5:
ldc2_w 0.0
dstore 15
Floop6:
dload 15
ldc2_w 10.0
dcmpg
iflt LcmpTrue6
iconst_0
goto LcmpStore6
LcmpTrue6:
iconst_1
LcmpStore6:
istore 35
iload 35
ifne FTrue6
goto FEnd6
FTrue6:
dload 31
d2i
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
dload 15
d2i
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
astore 33
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 33
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
dload 15
ldc2_w 1.0
dadd
dstore 15
goto Floop6
FEnd6:
dload 31
ldc2_w 1.0
dadd
dstore 31
goto Floop5
FEnd5:
dload 19
ldc2_w 0.0
dcmpg
ifgt LcmpTrue9
iconst_0
goto LcmpStore9
LcmpTrue9:
iconst_1
LcmpStore9:
dload 19
ldc2_w 1.0
dcmpg
iflt LcmpTrue10
iconst_0
goto LcmpStore10
LcmpTrue10:
iconst_1
LcmpStore10:
iand
istore 51
iload 51
ifne IFTrue11
goto IFEnd11
IFTrue11:
ldc "esta entre 0 e 1"
astore 39
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 39
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
dload 19
ldc2_w 0.5
dcmpg
ifgt LcmpTrue11
iconst_0
goto LcmpStore11
LcmpTrue11:
iconst_1
LcmpStore11:
istore 49
iload 49
ifne IFTrue12
ldc "e menor que 0.5"
astore 47
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 47
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto IFEnd12
IFTrue12:
ldc "e maior que 0.5"
astore 41
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 41
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload 21
ldc "ERRO "
invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I
ifne LcmpTrue12
iconst_0
goto LcmpStore12
LcmpTrue12:
iconst_1
LcmpStore12:
istore 45
iload 45
ifne IFTrue13
goto IFEnd13
IFTrue13:
ldc "O resultado e "
dload 19
invokestatic java/lang/String/valueOf(D)Ljava/lang/String;
invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
astore 43
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 43
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
IFEnd13:
IFEnd12:
IFEnd11:
dload 19
ldc2_w 1.0
dcmpg
ifgt LcmpTrue16
iconst_0
goto LcmpStore16
LcmpTrue16:
iconst_1
LcmpStore16:
istore 57
iload 57
ifne IFTrue17
ldc "else funcionando"
astore 55
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 55
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto IFEnd17
IFTrue17:
ldc "e maior que 1"
astore 53
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 53
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
IFEnd17:
ldc2_w 0.0
dstore 59
dload 59
dstore 61
dload 61
ldc2_w 0.0
dcmpg
ifne Case1False18
ldc "0"
astore 63
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 63
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto EndSwitch18
Case1False18:
dload 61
ldc2_w 1.0
dcmpg
ifne Case2False18
ldc "1"
astore 65
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 65
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto EndSwitch18
Case2False18:
dload 61
ldc2_w 2.0
dcmpg
ifne Case3False18
ldc "2"
astore 67
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 67
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto EndSwitch18
Case3False18:
dload 61
ldc2_w 3.0
dcmpg
ifne Case4False18
ldc "3"
astore 69
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 69
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto EndSwitch18
Case4False18:
ldc "maior que 3 o valor e "
dload 59
d2i
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
astore 71
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 71
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
EndSwitch18:
dload 59
ldc2_w 1.0
dadd
dstore 59
Wloop18:
dload 59
ldc2_w 10.0
dcmpg
iflt LcmpTrue19
iconst_0
goto LcmpStore19
LcmpTrue19:
iconst_1
LcmpStore19:
istore 73
iload 73
ifne WTrue18
goto WEnd18
WTrue18:
dload 59
dstore 61
dload 61
ldc2_w 0.0
dcmpg
ifne Case1False20
ldc "0"
astore 63
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 63
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto EndSwitch20
Case1False20:
dload 61
ldc2_w 1.0
dcmpg
ifne Case2False20
ldc "1"
astore 65
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 65
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto EndSwitch20
Case2False20:
dload 61
ldc2_w 2.0
dcmpg
ifne Case3False20
ldc "2"
astore 67
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 67
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto EndSwitch20
Case3False20:
dload 61
ldc2_w 3.0
dcmpg
ifne Case4False20
ldc "3"
astore 69
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 69
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
goto EndSwitch20
Case4False20:
ldc "maior que 3 o valor e "
dload 59
d2i
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
astore 71
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 71
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
EndSwitch20:
dload 59
ldc2_w 1.0
dadd
dstore 59
goto Wloop18
WEnd18:
ldc2_w 2.0
dstore 75
ldc2_w 9.0
dstore 77
Wloop22:
dload 75
ldc2_w 0.0
dcmpg
ifge LcmpTrue22
iconst_0
goto LcmpStore22
LcmpTrue22:
iconst_1
LcmpStore22:
istore 83
iload 83
ifne WTrue22
goto WEnd22
WTrue22:
Wloop23:
dload 77
ldc2_w 0.0
dcmpg
ifge LcmpTrue23
iconst_0
goto LcmpStore23
LcmpTrue23:
iconst_1
LcmpStore23:
istore 81
iload 81
ifne WTrue23
goto WEnd23
WTrue23:
dload 75
d2i
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
dload 77
d2i
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;
astore 79
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 79
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
dload 77
ldc2_w 1.0
dsub
dstore 77
goto Wloop23
WEnd23:
ldc2_w 9.0
dstore 77
dload 75
ldc2_w 1.0
dsub
dstore 75
goto Wloop22
WEnd22:
return
.end method
