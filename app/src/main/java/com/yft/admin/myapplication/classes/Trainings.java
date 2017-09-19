package com.yft.admin.myapplication.classes;


/**
 * Created by Admin on 17.03.2017.
 */

public final class Trainings {

    public static TrainingClass bottomZGant(int level) {
        TrainingClass res=new TrainingClass(3);
        res.isx4=true;
        res.name="Bottom Training";
        /////TODO: change amount
        int[] amount;
        switch (level){
            case 0:
                amount=new int[]{8,8,8};
                break;
            case 1:
                amount=new int[]{12,12,12};
                break;
            case 2:
                amount=new int[]{15,15,15};
                break;
            default:
                amount=new int[0];
        }
        res.addExercise(ExerciseClass.squatsWithDumbbell(amount[0]),0);
        res.addExercise(ExerciseClass.dumbbellSteppingLunge(amount[1]),1);
        res.addExercise(ExerciseClass.dumbbellBridging(amount[2]),2);
        return res;
    }

    public static TrainingClass bottomBezGant(int level){
        switch (level){
            case 0:
                return bottomBezGantEasy();
            case 1:
                return bottomBezGantMiddle();
            case 2:
                return bottomBezGantHard();
            default:
                return null;
        }
    }

    private static TrainingClass bottomBezGantEasy() {
        //TODO: change amount,add relax
        TrainingClass res=new TrainingClass(17);
        res.isx4=false;
        res.name="Bottom Training";
        res.addExercise(ExerciseClass.buttKicks(10),0);

        res.addExercise(ExerciseClass.recovery(15),1);
        res.addExercise(ExerciseClass.calfRaises(8),2);

        res.addExercise(ExerciseClass.recovery(30),3);
        res.addExercise(ExerciseClass.squats(8),4);

        res.addExercise(ExerciseClass.recovery(20),5);
        res.addExercise(ExerciseClass.buttKicks(10),6);

        res.addExercise(ExerciseClass.recovery(30),7);
        res.addExercise(ExerciseClass.calfRaises(10),8);
        res.addExercise(ExerciseClass.squats(8),9);

        res.addExercise(ExerciseClass.recovery(30),10);
        res.addExercise(ExerciseClass.squats(8),11);
        res.addExercise(ExerciseClass.calfRaises(10),12);

        res.addExercise(ExerciseClass.recovery(30),13);
        res.addExercise(ExerciseClass.lateralLunge(5),14);

        res.addExercise(ExerciseClass.recovery(30),15);
        res.addExercise(ExerciseClass.buttKicks(10),16);
        return res;
    }

    private static TrainingClass bottomBezGantMiddle() {
        TrainingClass res=new TrainingClass(18);
        res.isx4=false;
        res.name="Bottom Training";
        res.addExercise(ExerciseClass.buttKicks(16),0);
        res.addExercise(ExerciseClass.jumpingJacks(16),1);
        res.addExercise(ExerciseClass.bridgeCalfRaise(10),2);
        res.addExercise(ExerciseClass.squats(10),3);
        res.addExercise(ExerciseClass.calfRaises(10),4);

        res.addExercise(ExerciseClass.recovery(20),5);
        res.addExercise(ExerciseClass.calfRaises(10),6);
        res.addExercise(ExerciseClass.squatSideKick(8),7);
        res.addExercise(ExerciseClass.bridgeCalfRaise(10),8);

        res.addExercise(ExerciseClass.recovery(20),9);
        res.addExercise(ExerciseClass.squats(12),10);
        res.addExercise(ExerciseClass.lateralLunge(8),11);
        res.addExercise(ExerciseClass.bridgeCalfRaise(10),12);

        res.addExercise(ExerciseClass.recovery(20),13);
        res.addExercise(ExerciseClass.buttKicks(16),14);
        res.addExercise(ExerciseClass.squatSideKick(8),15);
        res.addExercise(ExerciseClass.bridgeCalfRaise(10),16);
        res.addExercise(ExerciseClass.lateralLunge(8),17);
        return res;
    }

    private static TrainingClass bottomBezGantHard() {
        TrainingClass res=new TrainingClass(17);
        res.isx4=false;
        res.name="Bottom Training";
        res.addExercise(ExerciseClass.buttKicks(10),0);
        res.addExercise(ExerciseClass.jumpingJacks(10),1);
        res.addExercise(ExerciseClass.bridgeCalfRaise(14),2);
        res.addExercise(ExerciseClass.squats(12),3);
        res.addExercise(ExerciseClass.calfRaises(12),4);
        res.addExercise(ExerciseClass.buttKicks(10),5);
        res.addExercise(ExerciseClass.calfRaises(10),6);
        res.addExercise(ExerciseClass.squatSideKick(10),7);

        res.addExercise(ExerciseClass.recovery(30),8);
        res.addExercise(ExerciseClass.bridgeCalfRaise(12),9);
        res.addExercise(ExerciseClass.squats(12),10);
        res.addExercise(ExerciseClass.lateralLunge(8),11);
        res.addExercise(ExerciseClass.bridgeCalfRaise(12),12);
        res.addExercise(ExerciseClass.buttKicks(14),13);
        res.addExercise(ExerciseClass.squatSideKick(10),14);
        res.addExercise(ExerciseClass.bridgeCalfRaise(12),15);
        res.addExercise(ExerciseClass.lateralLunge(8),16);
        return res;
    }

    public static TrainingClass fullBodyZGant(int level) {
        //TODO:add recovery
        TrainingClass res=new TrainingClass(8);
        res.isx4=true;
        res.name="Full Training";
        int[] amount;
         switch (level){
            case 0:
                amount=new int[]{8,8,8,8,8,8,8,8,8};
                break;
            case 1:
                amount=new int[]{12,12,12,12,12,12,12,12,12};
                break;
            case 2:
                amount=new int[]{15,15,15,15,15,15,15,15,15};
                break;
            default:
                amount=new int[0];
        }
        res.addExercise(ExerciseClass.squats(amount[0]), 0);
        res.addExercise(ExerciseClass.lunges(amount[1]), 1);
        res.addExercise(ExerciseClass.bridging(amount[2]), 2);
        res.addExercise(ExerciseClass.abs(amount[3]), 3);
        res.addExercise(ExerciseClass.shoulderFly(amount[4]), 4);
        res.addExercise(ExerciseClass.dumbbellOneArmTricepsExtension(amount[5]), 5);
        res.addExercise(ExerciseClass.dumbbellAlternateBicepCurl(amount[6]), 6);
        res.addExercise(ExerciseClass.pushUps(amount[7]), 7);
        return res;
    }

    public static TrainingClass fullBodyBezGant(int level) {
        TrainingClass res=new TrainingClass(6);
        res.isx4=true;
        res.name="Full Training";
        int[] amount;
        switch (level){
            case 0:
                amount=new int[]{8,8,8,8,8,8};
                break;
            case 1:
                amount=new int[]{12,12,12,12,12,12};
                break;
            case 2:
                amount=new int[]{15,15,15,15,15,15};
                break;
            default:
                amount=new int[0];
        }
        res.addExercise(ExerciseClass.abs(amount[0]), 0);
        res.addExercise(ExerciseClass.tricepsBenchDips(amount[1]), 1);
        res.addExercise(ExerciseClass.squats(amount[2]), 2);
        res.addExercise(ExerciseClass.plank(amount[3]), 3);
        res.addExercise(ExerciseClass.pushUps(amount[4]), 4);
        res.addExercise(ExerciseClass.lunges(amount[5]), 5);
        return res;
    }

    public static TrainingClass topZGant(int level) {
        TrainingClass res=new TrainingClass(7);
        res.isx4=true;
        res.name="Top Training";
        int[] amount;
        switch (level){
            case 0:
                amount=new int[]{8,8,8,8,8,8,8};
                break;
            case 1:
                amount=new int[]{12,12,12,12,12,12,12};
                break;
            case 2:
                amount=new int[]{15,15,15,15,15,15,15};
                break;
            default:
                amount=new int[0];
        }
        res.addExercise(ExerciseClass.abs(amount[0]), 0);
        res.addExercise(ExerciseClass.dumbbellRow(amount[1]), 1);
        res.addExercise(ExerciseClass.squatsWithDumbbell(amount[2]), 2);
        res.addExercise(ExerciseClass.dumbbellOneArmShoulderPress(amount[3]), 3);// по одний?
        res.addExercise(ExerciseClass.dumbbellOneArmTricepsExtension(amount[4]), 4);
        res.addExercise(ExerciseClass.dumbbellOneArmShoulderPress(amount[5]), 5);
        res.addExercise(ExerciseClass.dumbbellAlternateBicepCurl(amount[6]), 6);
        return res;
    }

    public static TrainingClass topBezGant(int level) {
        switch (level){
            case 0:
                return topBezGantEasy();
            case 1:
                return topBezGantMiddle();
            case 2:
                return topBezGantHard();
            default:
                return null;
        }
    }

    private static TrainingClass topBezGantHard() {
        TrainingClass res=new TrainingClass(19);
        res.isx4=false;
        res.name="Top Training";
        res.addExercise(ExerciseClass.kneePushUps(30),0);

        res.addExercise(ExerciseClass.recovery(20),1);
        res.addExercise(ExerciseClass.diamondPushUps(20),2);

        res.addExercise(ExerciseClass.recovery(20),3);
        res.addExercise(ExerciseClass.pushUps(30),4);

        res.addExercise(ExerciseClass.recovery(45),5);
        res.addExercise(ExerciseClass.spartanPushUps(10),6);

        res.addExercise(ExerciseClass.recovery(45),7);
        res.addExercise(ExerciseClass.pushUpsHandsAsChestWidth(10),8);

        res.addExercise(ExerciseClass.recovery(30),9);
        res.addExercise(ExerciseClass.hinduPushUps(10),10);

        res.addExercise(ExerciseClass.recovery(20),11);
        res.addExercise(ExerciseClass.explosivePushUps(10),12);

        res.addExercise(ExerciseClass.recovery(60),13);
        res.addExercise(ExerciseClass.pushUpsHandsAsChestWidth(10),14);
        res.addExercise(ExerciseClass.hinduPushUps(10),15);
        res.addExercise(ExerciseClass.explosivePushUps(10),16);

        res.addExercise(ExerciseClass.recovery(60),17);
        res.addExercise(ExerciseClass.diamondPushUps(20),18);
        return res;
    }

    private static TrainingClass topBezGantMiddle() {
        TrainingClass res=new TrainingClass(17);
        res.isx4=false;
        res.name="Top Training";
        res.addExercise(ExerciseClass.pushUps(15),0);

        res.addExercise(ExerciseClass.recovery(30),1);
        res.addExercise(ExerciseClass.pushUps(15),2);

        res.addExercise(ExerciseClass.recovery(30),3);
        res.addExercise(ExerciseClass.pushUps(15),4);
////
        res.addExercise(ExerciseClass.recovery(30),5);
        res.addExercise(ExerciseClass.pushUpsFeetOnBench(15),6);

        res.addExercise(ExerciseClass.recovery(30),7);
        res.addExercise(ExerciseClass.pushUpsFeetOnBench(15),8);

        res.addExercise(ExerciseClass.recovery(30),9);
        res.addExercise(ExerciseClass.pushUpsFeetOnBench(15),10);
////
        res.addExercise(ExerciseClass.recovery(30),11);
        res.addExercise(ExerciseClass.pushUpsHandsAsChestWidth(15),12);

        res.addExercise(ExerciseClass.recovery(30),13);
        res.addExercise(ExerciseClass.pushUpsHandsAsChestWidth(15),14);

        res.addExercise(ExerciseClass.recovery(30),15);
        res.addExercise(ExerciseClass.pushUpsHandsAsChestWidth(15),16);
        return res;
    }

    private static TrainingClass topBezGantEasy() {
        TrainingClass res=new TrainingClass(17);
        res.isx4=false;
        res.name="Top Training";
        res.addExercise(ExerciseClass.kneePushUps(12),0);

        res.addExercise(ExerciseClass.recovery(30),1);
        res.addExercise(ExerciseClass.kneePushUps(12),2);

        res.addExercise(ExerciseClass.recovery(30),3);
        res.addExercise(ExerciseClass.kneePushUps(12),4);

        res.addExercise(ExerciseClass.recovery(30),5);
        res.addExercise(ExerciseClass.pushUpsKneeOnBench(12),6);

        res.addExercise(ExerciseClass.recovery(30),7);
        res.addExercise(ExerciseClass.pushUpsKneeOnBench(12),8);

        res.addExercise(ExerciseClass.recovery(30),9);
        res.addExercise(ExerciseClass.pushUpsKneeOnBench(12),10);

        res.addExercise(ExerciseClass.recovery(30),11);
        res.addExercise(ExerciseClass.kneePushUpsHandsAsChestWidth(12),12);

        res.addExercise(ExerciseClass.recovery(30),13);
        res.addExercise(ExerciseClass.kneePushUpsHandsAsChestWidth(12),14);

        res.addExercise(ExerciseClass.recovery(30),15);
        res.addExercise(ExerciseClass.kneePushUpsHandsAsChestWidth(12),16);
        return res;
    }

    public static TrainingClass getStandartTraining(int pos,int isDumbbellOn,int level){
        switch (pos){
            case 0:
                switch (isDumbbellOn){
                    case 0:
                        return topBezGant(level);
                    case 1:
                        return topZGant(level);
                }
            case 1:
                switch (isDumbbellOn){
                    case 0:
                        return fullBodyBezGant(level);
                    case 1:
                        return fullBodyZGant(level);
                }
            case 2:
                switch (isDumbbellOn){
                    case 0:
                        return bottomBezGant(level);
                    case 1:
                        return bottomZGant(level);
                }
        }
        return null;
    }

}