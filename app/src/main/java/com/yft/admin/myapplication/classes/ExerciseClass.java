package com.yft.admin.myapplication.classes;


import com.yft.admin.myapplication.R;

/**
 * Created by Winchester on 04.03.2017.
 */

public class ExerciseClass {
    public String name;
    public int howManyTimes;
    public int url1;
    public int url2;
    public int info;


    public ExerciseClass(String name, int HowManyTimes, int url1,int url2,int info){
        this.name = name;
        this.howManyTimes = HowManyTimes;
        this.url1 = url1;
        this.url2=url2;
        this.info=info;
    }
    public ExerciseClass(String name, int HowManyTimes, int url1,int url2){
        this(name,HowManyTimes,url1,url2,0);
    }
    public ExerciseClass(String name, int HowManyTimes, int url1){
        this(name,HowManyTimes,url1,0);
    }


    public ExerciseClass(ExerciseClass temp){
        this.name = temp.name;
        this.howManyTimes = temp.howManyTimes;
        this.url1 = temp.url1;
        if(temp.url2!=0){
            this.url2=temp.url2;
        }
    }

    public static int getAmountOfExercises(){

        return 30;
    }

    public static ExerciseClass recovery(int seconds){
        ExerciseClass res=new ExerciseClass("Recovery", seconds, 0);
        return res;
    }

    public static ExerciseClass abs(int amount){
       int info=R.string.info_abs;
       int url= R.raw.anim_abs;
       return (new ExerciseClass("Abs",amount,url,0,info));
    }

    public static ExerciseClass buttKicks(int amount){
        int info=R.string.info_buttKicks;
        int url=R.raw.anim_jumps;
        return ( new ExerciseClass("Butt Kicks", amount,url,0,info));
    }

    public static ExerciseClass jumpingJacks(int amount){
        int info=R.string.info_jumpingJacks;
        int url=R.raw.anim_jumping_jacks;
        return ( new ExerciseClass("Jumping Jacks", amount,url,0,info));
    }

    public static ExerciseClass squats(int amount){
        int info=R.string.info_squats;
        int url=R.raw.anim_squats;
        return (new ExerciseClass("Squats",amount,url,0,info));
    }

    public static ExerciseClass squatsWithDumbbell(int amount){
        int info=R.string.info_squatsWithDumbbell;
        int url=R.raw.anim_squats_z_gant;
        return (new ExerciseClass("Squats With Dumbbell",amount,url,0,info));
    }

    public static ExerciseClass squatSideKick(int amount){
        int info=R.string.info_squatSideKick;
        int url=R.raw.anim_squats_z_nogoy;
        return (new ExerciseClass("Squat Side Kick",amount,url,0,info));
    }

    public static ExerciseClass lunges(int amount){
        int info=R.string.info_lunges;
        int url=R.raw.anim_vupadu;
        return (new ExerciseClass("Lunges",amount,url,0,info));
    }

    public static ExerciseClass bridging(int amount){
        int info=R.string.info_bridging;
        int url=R.raw.anim_pidyom_tazu;
        return (new ExerciseClass("Bridging",amount,url,0,info));
    }

    public static ExerciseClass dumbbellBridging(int amount){
        int info=R.string.info_dumbbellBridging;
        int url=R.raw.anim_pid_tazu_z_gant;
        return (new ExerciseClass("Dumbbell bridging",amount,url,0,info));
    }

    public static ExerciseClass bridgeCalfRaise(int amount){
        int info=R.string.info_bridgeCalfRaise;
        int url=R.raw.anim_ikru_lezha;
        return (new ExerciseClass("Bridge Calf Raise",amount,url,0,info));
    }

    public static ExerciseClass shoulderFly(int amount){
        int info=R.string.info_shoulderFly;
        int url1=R.raw.anim_mahu_left;
        int url2=R.raw.anim_mahu_right;
        return (new ExerciseClass("Shoulder fly",amount,url1,url2,info));
    }

    public static ExerciseClass dumbbellOneArmTricepsExtension(int amount){
        int info=R.string.info_dumbbellOneArmTricepsExtension;
        int url1=R.raw.anim_rozgun_left;
        int url2=R.raw.anim_rozgun_right;
        return (new ExerciseClass("Dumbbell one-arm triceps extension",amount,url1,url2,info));
    }

    public static ExerciseClass dumbbellAlternateBicepCurl(int amount){
        int info=R.string.info_dumbbellAlternateBicepCurl;
        int url1=R.raw.anim_bic_left;
        int url2=R.raw.anim_bic_right;
        return (new ExerciseClass("Dumbbell alternate bicep curl",amount,url1,url2,info));
    }

    public static ExerciseClass tricepsBenchDips(int amount){
        int info=R.string.info_tricepsBenchDips;
        int url=R.raw.anim_zvor_vidzhum;
        return (new ExerciseClass("Triceps bench dips",amount,url,0,info));
    }

    public static ExerciseClass plank(int amount){
        int info=R.string.info_plank;
        int url=R.raw.anim_plank;
        return (new ExerciseClass("Plank",amount,url,0,info));
    }

    public static ExerciseClass pushUps(int amount){
        int info=R.string.info_pushUps;
        int url=R.raw.anim_push_up;
        return ( new ExerciseClass("Push Ups", amount,url,0,info));
    }

    public static ExerciseClass kneePushUps(int amount){
        int info=R.string.info_kneePushUps;
        int url=R.raw.anim_puh_ups_from_knees;
        return (new ExerciseClass("Knee push ups",amount,url,0,info));
    }

    public static ExerciseClass pushUpsKneeOnBench(int amount){
        int info=R.string.info_pushUpsKneeOnBench;
        int url=R.raw.anim_push_ups_from_knees_z_lavku;
        return (new ExerciseClass("Push ups knee on bench",amount,url,0,info));
    }

    public static ExerciseClass pushUpsFeetOnBench(int amount){
        int info=R.string.info_pushUpsFeetOnBench;
        int url=R.raw.anim_push_up_vid_lavku;
        return (new ExerciseClass("Push ups feet on bench",amount,url,0,info));
    }

    public static ExerciseClass pushUpsHandsAsChestWidth(int amount){
        int info=R.string.info_pushUpsHandsAsChestWidth;
        int url=R.raw.anim_push_up_vuzko;
        return (new ExerciseClass("Push ups hands as chest width",amount,url,0,info));
    }

    public static ExerciseClass dumbbellOneArmShoulderPress(int amount){
        int info=R.string.info_dumbbellOneArmShoulderPress;
        int url1=R.raw.anim_gym_left;
        int url2=R.raw.anim_gym_right;
        return (new ExerciseClass("Dumbbell one-arm shoulder press",amount,url1,url2,info));
    }

    public static ExerciseClass calfRaises(int amount){
        int info=R.string.info_calfRaises;
        int url=R.raw.anim_na_ikru_stoya;
        return (new ExerciseClass("Calf raises",amount,url,0,info));
    }

    public static ExerciseClass lateralLunge(int amount){
        int info=R.string.info_lateralLunge;
        int url=R.raw.anim_lateral_lunge;
        return (new ExerciseClass("Lateral lunge",amount,url,0,info));
    }

    public static ExerciseClass dumbbellSteppingLunge(int amount){
        int info=R.string.info_dumbbellSteppingLunge;
        int url=R.raw.anim_vupadu_z_gant;
        return (new ExerciseClass("Dumbbell stepping lunge",amount,url,0,info));
    }

    public static ExerciseClass dumbbellRow(int amount) {
        int info=R.string.info_dumbbellRow;
        int url1=R.raw.anim_tyaga_gant_left_arm;
        int url2=R.raw.anim_tyaga_gant_right_arm;
        return (new ExerciseClass("Dumbbell Row",amount,url1,url2,info));
    }

    public static ExerciseClass kneePushUpsHandsAsChestWidth(int amount) {
        int info=R.string.info_kneePushUpsHandsAsChestWidth;
        int url=R.raw.anim_push_ups_from_knees_arms_under_chest;
        return (new ExerciseClass("Knee Push Ups Hands As Chest Width",amount,url,0,info));
    }

    public static ExerciseClass diamondPushUps(int amount) {
        int info=R.string.info_diamondPushUps;
        int url=R.raw.anim_push_up_vuzko_z_knees;
        return (new ExerciseClass("Diamond Push Ups",amount,url,0,info));
    }

    public static ExerciseClass spartanPushUps(int amount) {
        int info=R.string.info_spartanPushUps;
        int url=R.raw.anim_spartan_push_up;
        return (new ExerciseClass("Spartan Push Ups",amount,url,0,info));
    }

    public static ExerciseClass hinduPushUps(int amount) {
        int info=R.string.info_hinduPushUps;
        int url=R.raw.anim_hindu_push_ups;
        return (new ExerciseClass("Hindu Push Ups",amount,url,0,info));
    }

    public static ExerciseClass explosivePushUps(int amount) {
        int info=R.string.info_explosivePushUps;
        int url=R.raw.anim_explosive_push_ups;
        return (new ExerciseClass("Explosive Push Ups",amount,url,0,info));
    }

    public static String[] getAllExercisesNames(){
        String[] result=new String[getAmountOfExercises()];
        result[0]="Abs";
        result[1]="Explosive\nPush Ups";
        result[2]="Push Ups";
        result[3]="Squats";
        result[4]="Lunges";
        result[5]="Bridging";
        result[6]="Dumbbell\nbridging";
        result[7]="Shoulder\nfly";
        result[8]="Dumbbell\none-arm\ntriceps\nextension";
        result[9]="Dumbbell\nalternate\nbicep\ncurl";
        result[10]="Triceps\nbench\ndips";
        result[11]="Plank";
        result[12]="Knee\npush\nups";
        result[13]="Push ups\nknee on\nbench";
        result[14]="Push ups\nfeet on\nbench";
        result[15]="Push ups\nhands as\nchest width";
        result[16]="Dumbbell\none-arm\nshoulder\npress";
        result[17]="Calf\nraises";
        result[18]="Lateral\nlunge";
        result[19]="Dumbbell\nstepping\nlunge";
        result[20]="Butt\nKicks";
        result[21]="Squats\nWith\nDumbbell";
        result[22]="Jumping\nJacks";
        result[23]="Bridge\nCalf\nRaise";
        result[24]="Squat\nSide\nKick";
        result[25]="Dumbbell\nRow";
        result[26]="Knee\nPush Ups\nHands As\nChest Width";
        result[27]="Diamond\nPush Ups";
        result[28]="Spartan\nPush Ups";
        result[29]="Hindu\nPush Ups";
        return result;
    }


    //Function<ExerciseClass,ExerciseClass>
    public static ExerciseClass getFunkByIndex(int ind,int amount){
        switch (ind){
            case 0:
                return abs(amount);
            case 1:
                return explosivePushUps(amount);
            case 2:
                return pushUps(amount);
            case 3:
                return squats(amount);
            case 4:
                return lunges(amount);
            case 5:
                return bridging(amount);
            case 6:
                return dumbbellBridging(amount);
            case 7:
                return shoulderFly(amount);
            case 8:
                return dumbbellOneArmTricepsExtension(amount);
            case 9:
                return dumbbellAlternateBicepCurl(amount);
            case 10:
                return tricepsBenchDips(amount);
            case 11:
                return plank(amount);
            case 12:
                return kneePushUps(amount);
            case 13:
                return pushUpsKneeOnBench(amount);
            case 14:
                return pushUpsFeetOnBench(amount);
            case 15:
                return pushUpsHandsAsChestWidth(amount);
            case 16:
                return dumbbellOneArmShoulderPress(amount);
            case 17:
                return calfRaises(amount);
            case 18:
                return lateralLunge(amount);
            case 19:
                return dumbbellSteppingLunge(amount);
            case 20:
                return buttKicks(amount);
            case 21:
                return squatsWithDumbbell(amount);
            case 22:
                return jumpingJacks(amount);
            case 23:
                return bridgeCalfRaise(amount);
            case 24:
                return squatSideKick(amount);
            case 25:
                return dumbbellRow(amount);
            case 26:
                return kneePushUpsHandsAsChestWidth(amount);
            case 27:
                return diamondPushUps(amount);
            case 28:
                return spartanPushUps(amount);
            case 29:
                return hinduPushUps(amount);
            default:
                return null;
        }
    }

    public static ExerciseClass getExerciseByName(String name,int amount) {
        for (int i = 0; i < getAmountOfExercises(); i++) {
            ExerciseClass res = getFunkByIndex(i, amount);
            if (res.name.equals(name)) {
                return res;
            }
        }
        return null;
    }

    public static  ExerciseClass getExerciseByName(String name) {
        return getExerciseByName(name,0);
    }
}
