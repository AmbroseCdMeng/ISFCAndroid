package com.maci.foxconn.isfcandroid;

import java.io.Serializable;
import java.util.List;

public class Beans implements Serializable {

    private Boolean status;
    private String message;
    private List<StorageForm> result;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StorageForm> getResult() {
        return result;
    }

    public void setResult(List<StorageForm> result) {
        this.result = result;
    }

    public static class StorageForm implements Serializable {
        private String formno;
        private String dptno;
        private String dptname;
        private String formStatus;
        private String formStatusName;
        private List<StorageDetail> children;

        public String getFormno() {
            return formno;
        }

        public void setFormno(String formno) {
            this.formno = formno;
        }

        public String getDptno() {
            return dptno;
        }

        public void setDptno(String dptno) {
            this.dptno = dptno;
        }

        public String getDptname() {
            return dptname;
        }

        public void setDptname(String dptname) {
            this.dptname = dptname;
        }

        public String getFormStatus() {
            return formStatus;
        }

        public void setFormStatus(String formStatus) {
            this.formStatus = formStatus;
        }

        public String getFormStatusName() {
            return formStatusName;
        }

        public void setFormStatusName(String formStatusName) {
            this.formStatusName = formStatusName;
        }

        public List<StorageDetail> getChildren() {
            return children;
        }

        public void setChildren(List<StorageDetail> children) {
            this.children = children;
        }

        public static class  StorageDetail implements Serializable{
            private String mtlno;
            private String prodname;
            private String planqty;
            private String actualqty;
            private String unit;
            private boolean select;

            public String getMtlno() {
                return mtlno;
            }

            public void setMtlno(String mtlno) {
                this.mtlno = mtlno;
            }

            public String getProdname() {
                return prodname;
            }

            public void setProdname(String prodname) {
                this.prodname = prodname;
            }

            public String getPlanqty() {
                return planqty;
            }

            public void setPlanqty(String planqty) {
                this.planqty = planqty;
            }

            public String getActualqty() {
                return actualqty;
            }

            public void setActualqty(String actualqty) {
                this.actualqty = actualqty;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }
        }
    }
}
