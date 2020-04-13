package com.maci.foxconn.isfcandroid;

import java.io.Serializable;

public class Beans implements Serializable {

    public static class Storage implements Serializable {

        private String workOrder;//工令单号
        private String payDepartment;//缴库部门
        private String materialNum;//物料号
        private String materialName;//物料名称

        public String getWorkOrder() {
            return workOrder;
        }

        public void setWorkOrder(String workOrder) {
            this.workOrder = workOrder;
        }

        public String getPayDepartment() {
            return payDepartment;
        }

        public void setPayDepartment(String payDepartment) {
            this.payDepartment = payDepartment;
        }

        public String getMaterialNum() {
            return materialNum;
        }

        public void setMaterialNum(String materialNum) {
            this.materialNum = materialNum;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }
    }

    public static class InStorage extends Storage {
        private String inStorageStatus;//入库状态（未入库、部分入库、已入库）

        private String planInStorageCount;//计划入库数量
        private String actInStorageCount;//实际入库数量
        private String locationCode;//储位码

        private String palletCount;//栈板数量
        private String packageCount;//包箱数量
        private String materialCount;//物料数量

        private String palletCode;//栈板码
        private String packageCode;//箱码

        private String asnCode;//ASN 码
        private String relatedStatus;//关联状态

        public String getInStorageStatus() {
            return inStorageStatus;
        }

        public void setInStorageStatus(String inStorageStatus) {
            this.inStorageStatus = inStorageStatus;
        }

        public String getPlanInStorageCount() {
            return planInStorageCount;
        }

        public void setPlanInStorageCount(String planInStorageCount) {
            this.planInStorageCount = planInStorageCount;
        }

        public String getActInStorageCount() {
            return actInStorageCount;
        }

        public void setActInStorageCount(String actInStorageCount) {
            this.actInStorageCount = actInStorageCount;
        }

        public String getLocationCode() {
            return locationCode;
        }

        public void setLocationCode(String locationCode) {
            this.locationCode = locationCode;
        }

        public String getPalletCount() {
            return palletCount;
        }

        public void setPalletCount(String palletCount) {
            this.palletCount = palletCount;
        }

        public String getPackageCount() {
            return packageCount;
        }

        public void setPackageCount(String packageCount) {
            this.packageCount = packageCount;
        }

        public String getMaterialCount() {
            return materialCount;
        }

        public void setMaterialCount(String materialCount) {
            this.materialCount = materialCount;
        }

        public String getPalletCode() {
            return palletCode;
        }

        public void setPalletCode(String palletCode) {
            this.palletCode = palletCode;
        }

        public String getPackageCode() {
            return packageCode;
        }

        public void setPackageCode(String packageCode) {
            this.packageCode = packageCode;
        }

        public String getAsnCode() {
            return asnCode;
        }

        public void setAsnCode(String asnCode) {
            this.asnCode = asnCode;
        }

        public String getRelatedStatus() {
            return relatedStatus;
        }

        public void setRelatedStatus(String relatedStatus) {
            this.relatedStatus = relatedStatus;
        }
    }

    public static class OutStorage extends Storage {
        private String outStorageStatus;//出库状态（未出库、部分出库、已出库）

        private String planOutStorageCount;//计划出库数量
        private String actOutStorageCount;//实际出库数量

        public String getOutStorageStatus() {
            return outStorageStatus;
        }

        public void setOutStorageStatus(String outStorageStatus) {
            this.outStorageStatus = outStorageStatus;
        }

        public String getPlanOutStorageCount() {
            return planOutStorageCount;
        }

        public void setPlanOutStorageCount(String planOutStorageCount) {
            this.planOutStorageCount = planOutStorageCount;
        }

        public String getActOutStorageCount() {
            return actOutStorageCount;
        }

        public void setActOutStorageCount(String actOutStorageCount) {
            this.actOutStorageCount = actOutStorageCount;
        }
    }
}
