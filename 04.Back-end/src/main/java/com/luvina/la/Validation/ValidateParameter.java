package com.luvina.la.Validation;
import com.luvina.la.entity.Certification;
import com.luvina.la.entity.Department;
import com.luvina.la.entity.Employee;
import static com.luvina.la.common.Message.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * class thực hiện validate các parameter
 * @author thaonv
 */
public class ValidateParameter {
    /**
     * method validate employeeLoginId
     * @param employeeLoginId employeeLoginId gửi lên từ client
     * @param employee thông tin employee có chứa employeeLoginId hay khong
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateEmployeeLoginId(String employeeLoginId, Optional<Employee> employee) {
        Map<String, Object> response = new HashMap<>();
        String pattern = "^[a-zA-Z][a-zA-Z0-9]*$";
        if(employeeLoginId.isEmpty()) {
            response.put("ER001","[アカウント名] "+ER001);
        }else if(employeeLoginId.length() > 50) {
            response.put("ER006","[アカウント名] "+ER006);
        }else if(!employeeLoginId.matches(pattern)) {
            response.put("ER019","[アカウント名] "+ER019);
        }else if(employee.isPresent()) {
            response.put("ER003","[アカウント名] "+ER003);
        }
        return response;
    }
    /**
     * method validate employeeName
     * @param employeeName employeeName gửi lên từ client
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateEmployeeName(String employeeName) {
        Map<String, Object> response = new HashMap<>();
        if(employeeName.isEmpty()) {
            response.put("ER001","[氏名] " + ER001);
        }else if(employeeName.length() > 125) {
            response.put("ER006","[氏名] " + ER006);
        }
        return response;
    }
    /**
     * method validate employeeBirthDate
     * @param employeeBirthDate employeeBirthDate gửi lên từ client
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateEmployeeBirthDate(String employeeBirthDate) {
        String pattern = "^\\d{4}-\\d{2}-\\d{2}$";
        Map<String, Object> response = new HashMap<>();
        if(employeeBirthDate.isEmpty()) {
            response.put("ER001","[生年月日] " + ER001);
        }else if(employeeBirthDate.matches(pattern)){
            response.put("ER005","[生年月日] " + ER005);
        }else if(!isValidDate(employeeBirthDate)) {
            response.put("ER011","[生年月日] " + ER011);
        }
        return response;
    }
    /**
     * method validate employeeEmail
     * @param employeeEmail employeeEmail gửi lên từ client
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateEmployeeEmail(String employeeEmail) {
        Map<String, Object> response = new HashMap<>();
        //String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(employeeEmail.isEmpty()) {
            response.put("ER001", "[メールアドレス] " + ER001);
        } else if(employeeEmail.length() > 125 ) {
            response.put("ER006", "[メールアドレス] " + ER006);
        }
        return response;
    }
    /**
     * method validate employeePhone
     * @param employeePhone employeePhone gửi lên từ client
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateEmployeePhone(String employeePhone) {
        Map<String, Object> response = new HashMap<>();
        if(employeePhone.isEmpty()) {
            response.put("ER001", "[電話番号] " + ER001);
        }else if(employeePhone.length() > 50) {
            response.put("ER008", "[電話番号] " + ER008);
        }
        return response;
    }
    /**
     * method validate employeeLoginPassword
     * @param employeeLoginPassword employeeLoginPassword gửi lên từ client
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateEmployeeLoginPassword(String employeeLoginPassword) {
        Map<String, Object> response = new HashMap<>();
        if(employeeLoginPassword.isEmpty()) {
            response.put("ER001", "[パスワード] " + ER001);
        }else if(employeeLoginPassword.length() < 8 && employeeLoginPassword.length() > 50) {
            response.put("ER007", "[パスワード] " + ER007);
        }
        return response;
    }
    /**
     * method validate departmentId
     * @param departmentId departmentId gửi lên từ client
     * @param department thông tin department của departmentId có tồn tại hay không
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateDepartmentId(Long departmentId, Optional<Department> department) {
        Map<String, Object> response = new HashMap<>();
        if(department == null) {
            response.put("ER002", "[グループ] " + ER002);
        } else if (departmentId < 0) {
            response.put("ER018", "[グループ] " + ER018);
        } else if (!department.isPresent()) {
            response.put("ER004", "[グループ] " + ER004);
        }
        return response;
    }
    /**
     * method validate certificationStartDate
     * @param certificationStartDate certificationStartDate gửi lên từ client
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateCertificationStartDate(String certificationStartDate) {
        String pattern = "^\\d{4}-\\d{2}-\\d{2}$";
        Map<String, Object> response = new HashMap<>();
        if(certificationStartDate.isEmpty()) {
            response.put("ER001","[資格交付日] " +ER001);
        } else if (certificationStartDate.matches(pattern)) {
            response.put("ER005","[資格交付日] " +ER005);
        } else if(!isValidDate(certificationStartDate)) {
            response.put("ER011","[資格交付日] " +ER011);
        }
        return response;
    }
    /**
     * method validate certificationEndDate
     * @param certificationEndDate certificationEndDate gửi lên từ client
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateCertificationEndDate(String certificationEndDate) {
        String pattern = "^\\d{4}-\\d{2}-\\d{2}$";
        Map<String, Object> response = new HashMap<>();
        if(certificationEndDate.isEmpty()) {
            response.put("ER001", "[失効日] " +ER001);
        } else if (certificationEndDate.matches(pattern)) {
            response.put("ER005", "[失効日] " + ER005);
        } else if(!isValidDate(certificationEndDate)) {
            response.put("ER011", "[失効日] " +ER011);
        }
        return response;
    }

    /**
     * method check ngày hết hạn có lớn hơn ngày bắt đâuf không
     * @param endDate ngày hết hạn chứng chỉ
     * @param startDate ngày bắt đầu chứng chỉ
     * @return Map<String, Object> chứa mã code lỗi và thông báo lỗi nếu có
     */
    public Map<String, Object> checkEndDateGreaterStartDate(Date endDate,Date startDate) {
        Map<String, Object> response = new HashMap<>();
        if(endDate.compareTo(startDate) < 0) {
            response.put("ER012", ER012);
        }
        return response;
    }
    /**
     * method validate certificationId
     * @param certificationId certificationId gửi lên từ client
     * @param certification thông tin certification của certificationId có tồn tại hay không
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateCertificationId(Long certificationId, Optional<Certification> certification) {
        Map<String, Object> response = new HashMap<>();
        if(certificationId == null) {
            response.put("ER002", "[資格] " + ER002);
        } else if (certificationId < 0) {
            response.put("ER018", "[資格] " + ER018);
        } else if (!certification.isPresent()) {
            response.put("ER004", "[資格] " + ER004);
        }
        return response;
    }
    /**
     * method validate certificationScore
     * @param certificationScore certificationScore gửi lên từ client
     * @return Map<String, Object> chứa mã code của lỗi và thông báo lỗi
     */
    public Map<String, Object> validateScore(BigDecimal certificationScore) {
        Map<String, Object> response = new HashMap<>();
        if(certificationScore == null) {
            response.put("ER002", "[点数] " + ER002);
        } else if (certificationScore.compareTo(BigDecimal.ZERO) < 0) {
            response.put("ER018", "[点数] " + ER018);
        }
        return response;
    }
    /**
     * method kiểm tra ngày hợp lệ hay không
     * @param dateString chuỗi date truyền vào
     * @return ngày có hợp lệ hay không
     */
    public boolean isValidDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false); // Không cho phép chuyển đổi ngày không hợp lệ (ví dụ: 2023/02/30)

        try {
            sdf.parse(dateString);
            return true; // Kiểm tra xem ngày sau khi chuyển đổi có giống với ngày ban đầu không
        } catch (ParseException e) {
            return false; // Không thể chuyển đổi thành công, ngày không hợp lệ
        }
    }
}
