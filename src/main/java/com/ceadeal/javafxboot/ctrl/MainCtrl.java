package com.ceadeal.javafxboot.ctrl;

import com.ceadeal.javafxboot.service.CaptureService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;
import org.jnetpcap.PcapSockAddr;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 主界面控制器
 *
 * @author westinyang
 * @date 2019/4/23 2:01
 */
@Slf4j
@FXMLController
public class MainCtrl implements Initializable {

    private static final String WANT_CAPTURE_IP = "192.168.43.245";
    private static PcapIf want_capture_device;
    // 主容器
    public Pane rootPane;
    public Button btnAlert, btnChooseFile;
    @Autowired
    private CaptureService captureService;

    public static String byteArrayToIp(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bs.length; i++) {
            if (i != bs.length - 1) {
                sb.append(bs[i] & 0xff).append(".");
            } else {
                sb.append(bs[i] & 0xff);
            }
        }
        return sb.toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // log.info("initialize: {}", location.getPath());
        String path = System.getProperty("java.library.path");
        log.info("path={}", path);
    }

    /**
     * 弹出框按钮单击事件
     *
     * @param actionEvent
     */
    public void onBtnAlertClick(ActionEvent actionEvent) {

        List<PcapIf> pcapIf = captureService.getPcapIf();
        for (PcapIf dev : pcapIf) {
            for (PcapAddr address : dev.getAddresses()) {
                PcapSockAddr addr = address.getAddr();
                String ip = byteArrayToIp(addr.getData());
                if (WANT_CAPTURE_IP.equals(ip)) {
                    want_capture_device = dev;
                    break;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("成功获取到IP：" + WANT_CAPTURE_IP + "的网卡名称：" + want_capture_device.getName());
        alert.show();
        log.info("want_capture_device = " + want_capture_device);
    }

    /**
     * 选择文件按钮单机事件
     *
     * @param actionEvent
     */
    public void onBtnChooseFileClick(ActionEvent actionEvent) {
        Window window = rootPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        // 文件类型过滤器
        /*FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel files (*.xls, *.xlsx)", "*.xls", "*.xlsx");
        fileChooser.getExtensionFilters().add(filter);*/
        // ...
        File file = fileChooser.showOpenDialog(window);
        String fileName = file == null ? "" : file.getName();
        String fileAbsolutePath = file == null ? "" : file.getAbsolutePath();

        if (file != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("文件路径：" + fileAbsolutePath);
            alert.show();
        }
    }
}
