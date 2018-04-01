package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Conf;
import net.coobird.thumbnailator.geometry.Positions;
import service.ImageHandler;
import service.QuickHandler;
import view.MainView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MainController {

    private MainView mainView;

    @FXML
    private ComboBox<String> scaleTypeBox;

    @FXML
    private TextField scaleW;

    @FXML
    private TextField scaleH;

    @FXML
    private TextField scaleB;

    @FXML
    private CheckBox rotationBox;

    @FXML
    private TextField rotationCount;

    @FXML
    private CheckBox retroBox;

    @FXML
    private ComboBox<String> retroTypeBox;

    @FXML
    private CheckBox addWater;

    @FXML
    private TextField waterPath;

    @FXML
    private Button selWaterPath;

    @FXML
    private CheckBox waterTextBox;

    @FXML
    private TextField waterText;

    @FXML
    private Slider waterTransSlider;

    @FXML
    private TextField waterTransCount;

    @FXML
    private ComboBox<String> waterPositionType;

    @FXML
    private Slider imageQualitySlider;

    @FXML
    private TextField imageQualityCount;

    @FXML
    private ComboBox<String> imageFormatType;

    @FXML
    private TextField outputPath;

    @FXML
    private Button selOutputButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu about;

    @FXML
    private Label waning;

    private Conf conf = null;


    public void setMainView(MainView mainView) {
        this.mainView = mainView;
        initConf();
        initView();
    }

    private void initView(){

        initScaleTypeComboBox();
        initRotation();
        initRetro();
        initWaterAdd();
        initTextWater();
        initWaterTrans();
        initWaterPosition();
        initQuality();
        initFormat();
        initOutputPath();
        initDropAction();

    }

    private void setFailBorder(TextField textField){
        textField.setStyle("-fx-border-color:red");

    }

    private void setNormalBorder(TextField textField){

        textField.setStyle("-fx-border-color:#00000000");
    }

    private boolean checkNumber(String value){
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return value.matches(regex);
    }

    /**
     * 初始化缩放方式
     */
    private void initScaleTypeComboBox(){

        ObservableList<String> observable= FXCollections.observableArrayList("保留原格式","百分比模式","自定义");

        if (scaleTypeBox!=null) {
            scaleTypeBox.setItems(observable);
            scaleTypeBox.setValue("保留原格式");

            conf.setSCALE_TYPE(10);

            scaleTypeBox.valueProperty().addListener((observable1, oldValue, newValue) -> {


                switch (newValue){
                    case "保留原格式":
                        conf.setSCALE_TYPE(10);

                        scaleB.setDisable(true);
                        scaleW.setDisable(true);
                        scaleH.setDisable(true);
                        break;
                    case "百分比模式":
                        conf.setSCALE_TYPE(20);
                        scaleB.setDisable(false);
                        scaleW.setDisable(true);
                        scaleH.setDisable(true);
                        break;
                    case "自定义":
                        conf.setSCALE_TYPE(30);
                        scaleB.setDisable(true);
                        scaleW.setDisable(false);
                        scaleH.setDisable(false);
                        break;
                }
            });

            scaleH.textProperty().addListener((observable12, oldValue, newValue) -> {
                if (checkNumber(newValue)||newValue.isEmpty()){

                    float f= Float.parseFloat(newValue);

                    if (f<0){
                        setFailBorder(scaleH);

                    }else {
                        conf.setScaleH(Float.parseFloat(newValue));
                        setNormalBorder(scaleH);
                    }

                }else {

                    conf.setScaleH(0);
                    setFailBorder(scaleH);
                }
            });

            scaleW.textProperty().addListener((observable12, oldValue, newValue) -> {

                if (checkNumber(newValue)||newValue.isEmpty()){

                    float f= Float.parseFloat(newValue);

                    if (f<0){
                        setFailBorder(scaleW);

                    }else {
                        conf.setScaleW(Float.parseFloat(newValue));
                        setNormalBorder(scaleW);
                    }

                }else {

                    conf.setScaleW(0);
                    setFailBorder(scaleW);
                }

            });

            scaleB.textProperty().addListener((observable12, oldValue, newValue) -> {

                if (checkNumber(newValue)||newValue.isEmpty()){

                    float f= Float.parseFloat(newValue);

                    if (f<0){
                        setFailBorder(scaleB);

                    }else {
                        conf.setScaleB(Float.parseFloat(newValue));
                        setNormalBorder(scaleB);
                    }

                }else {
                    conf.setScaleB(0);
                    setFailBorder(scaleB);
                }

            });

        }

    }


    private void initConf(){
        conf=new Conf();
    }


    /**
     * 初始化旋转图片操作
     */
    private void initRotation(){
        if (this.rotationBox==null){
            return;
        }

        this.rotationBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){

                rotationCount.setDisable(false);
            }else {
                rotationCount.setDisable(true);
            }

            conf.setRotation(newValue);



        });

        //允许负数
        this.rotationCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (checkNumber(newValue)){
                conf.setRotationCount(Integer.parseInt(newValue));
                setNormalBorder(rotationCount);
            }else {
                conf.setRotationCount(0);
                setFailBorder(rotationCount);
            }
        });

    }

    /**
     * 初始化翻转图片
     */
    private void initRetro(){
        if (retroBox==null){
            return;
        }

        retroTypeBox.setValue("上下翻转");

        retroBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                retroTypeBox.setDisable(false);

                conf.setRetroType(retroTypeBox.getValue());
            }else {
                retroTypeBox.setDisable(true);
            }

            conf.setRetro(newValue);

        });

        retroTypeBox.getItems().addAll("上下翻转","左右翻转");

        retroTypeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            conf.setRetroType(newValue);
        });

    }

    /**
     * 水印添加
     */
    private void initWaterAdd(){

        if (addWater==null){
            return;
        }
        waterPath.setEditable(false);

        addWater.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue){
                selWaterPath.setDisable(false);
                waterTextBox.setDisable(false);
                waterTransSlider.setDisable(false);
                waterTransCount.setDisable(false);
                waterPositionType.setDisable(false);

                if (waterTextBox.isSelected()){
                    waterText.setDisable(false);
                }

            }else {
                selWaterPath.setDisable(true);
                waterTextBox.setDisable(true);
                waterTransSlider.setDisable(true);
                waterTransCount.setDisable(true);
                waterPositionType.setDisable(true);
                waterText.setDisable(true);
            }

            conf.setAddWater(newValue);
        });


        selWaterPath.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择水印图片");

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );

            Stage stage=mainView.getStage();
            File file=fileChooser.showOpenDialog(stage);

            String path=file.getAbsolutePath();

            waterPath.setText(path);

            conf.setWaterPath(path);
        });

    }

    /**
     * 添加文字水印
     */
    private void initTextWater(){
        if (waterTextBox==null){

            return;
        }

        waterTextBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){

                waterText.setDisable(false);
                selWaterPath.setDisable(true);
            }else {
                waterText.setDisable(true);
                selWaterPath.setDisable(false);

            }

            conf.setAddTextWater(newValue);

        });

        waterText.textProperty().addListener((observable, oldValue, newValue) -> {
            conf.setTextWater(newValue);
        });

    }

    private void initWaterTrans(){
        if (this.waterTransSlider==null){
            return;
        }

        this.waterTransSlider.valueProperty().addListener((observable, oldValue, newValue) -> {


            int s= newValue.intValue();

            waterTransCount.setText(String.valueOf(s));


        });

        conf.setWaterTrans(1f);

        this.waterTransCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (checkNumber(newValue)){
                int n= Integer.parseInt(newValue);
                if (n<0){

                    conf.setWaterTrans(0);//默认透明度为0
                    setFailBorder(waterTransCount);
                }else {

                    waterTransSlider.setValue(n);

                    if (n>100){
                        n=100;
                    }

                    float f= (float) (n/100.0);
                    conf.setWaterTrans(f);
                    setNormalBorder(waterTransCount);
                }
            }else {
                conf.setWaterTrans(0);
                setFailBorder(waterTransCount);
            }
        });


    }

    private void initWaterPosition(){

        if (waterPositionType==null){
            return;
        }

        waterPositionType.getItems().addAll("左上角","左边","左下角","中上","中间","中下","右上角","右边","右下角");

        waterPositionType.setValue("左上角");
        conf.setPositions(Positions.TOP_LEFT);//初始化

        waterPositionType.valueProperty().addListener((observable, oldValue, newValue) -> {
           switch (newValue){
               case "左上角":
                   conf.setPositions(Positions.TOP_LEFT);
                   break;
               case "左边":
                   conf.setPositions(Positions.CENTER_LEFT);
                   break;
               case "左下角":
                   conf.setPositions(Positions.BOTTOM_LEFT);
                   break;
               case "中上":
                   conf.setPositions(Positions.TOP_CENTER);
                   break;
               case "中间":
                   conf.setPositions(Positions.CENTER);
                   break;
               case "中下":
                   conf.setPositions(Positions.BOTTOM_CENTER);
                   break;
               case "右边":
                   conf.setPositions(Positions.CENTER_RIGHT);
                   break;
               case "右上角":
                   conf.setPositions(Positions.TOP_RIGHT);
                   break;
               case "右下角":
                   conf.setPositions(Positions.BOTTOM_RIGHT);
                   break;
           }
        });

    }

    private void initQuality(){

        if (this.imageQualitySlider==null){
            return;
        }

        this.imageQualitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int s=newValue.intValue();

            imageQualityCount.setText(String.valueOf(s));

        });

        conf.setImageQuality(1f);

        this.imageQualityCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (checkNumber(newValue)){

                int s= Integer.parseInt(newValue);
                if (s<0){
                    conf.setImageQuality(1f);
                    setFailBorder(imageQualityCount);

                }else {

                    if (s>100){
                        s=100;
                    }

                    float q= (float) (s/100.0);
                    conf.setImageQuality(q);
                    setNormalBorder(imageQualityCount);
                }


            }else {
                conf.setImageQuality(1f);
                setFailBorder(imageQualityCount);
            }
        });

    }

    private void initFormat(){

        if (this.imageFormatType==null){
            return;
        }

        this.imageFormatType.getItems().addAll("不改变","jpg","png","gif","bmp");

        imageFormatType.setValue("不改变");

        conf.setFormat("不改变");

        imageFormatType.valueProperty().addListener((observable, oldValue, newValue) -> {

            conf.setFormat(newValue);
        });


    }

    private void initOutputPath(){


        selOutputButton.setOnAction(event -> {

            DirectoryChooser chooser=new DirectoryChooser();

            chooser.setTitle("选择输出路径");
            Stage stage=mainView.getStage();

            File file=chooser.showDialog(stage);

            if (file!=null&&file.isDirectory()) {
                String path = file.getAbsolutePath();

                outputPath.setText(path);

                conf.setOutputPath(path);

            }
        });
    }

    private void initDropAction(){

        Scene scene=mainView.getStage().getScene();

        scene.setOnDragOver(event -> {

            event.acceptTransferModes(TransferMode.ANY);//需要改变注册，方便在松手的时候让setOnDragDropped监听到

        });

        scene.setOnDragDropped(event -> {
            if (conf.getOutputPath()==null||conf.getOutputPath().isEmpty()){
                waning.setVisible(true);
                return;
            }else {
                waning.setVisible(false);
            }

            Dragboard dragboard = event.getDragboard();
            List<File> files = dragboard.getFiles();
            if(files.size() > 0){
                try {

                    QuickHandler handler=new ImageHandler();

                    for (File file : files) {
                        handler.handlerImage(conf, file);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }




}
