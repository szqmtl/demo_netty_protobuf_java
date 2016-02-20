#!/usr/bin/perl
use strict;
use warnings FATAL => 'all';

my $param = join(" ", @ARGV);
print "$param";
my $jarLibrary = " -classpath \"target/NettyProtoc-1.0-jar-with-dependencies.jar\" ";
my $logFile = " ";
my $rptDef = " ";
my $rptOutput = " ";
my $cmd =  "java " . $jarLibrary . $logFile . $rptDef . $rptOutput .
                " org.shu.zq.nettyprotoc.Server " . $param;
printf("%s\n", $cmd);
system($cmd);