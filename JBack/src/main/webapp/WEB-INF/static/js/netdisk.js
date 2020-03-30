// columns
var fileNameCol = document.getElementById("name-col");
const fileSizeCol = document.getElementById("size-col");
const fileDateCol = document.getElementById("date-col");

const fileTable = document.getElementById("file-table").getElementsByTagName("tbody")[0];

function NetFile(name, size, date, isDir) {
    this.name = name;
    this.size = nbyte2string(size);
    // this.date = date;
    this.selected = false;
    this.isDir = isDir;

    // date transform
    // with input ms to output formatted string
    const dateTime = date;
    const presentObj = new Date();
    const dateObj = new Date(date);
    const diffDate = presentObj - dateObj;
    if (diffDate < 30 * 1000) { // 30s
        this.date = "A few Seconds ago";
    } else if (diffDate < 1000 * 60 * 10) { // 10 min
        this.date = "A few minutes ago";
    } else {
        this.date = dateObj.toLocaleDateString();
    }
}
NetFile.prototype.tr2obj = new Map();
NetFile.prototype.selectedSet = new Set();
NetFile.prototype.putOnPage = function () {
    const tr = document.createElement("tr");
    function addTd(tr, text) {
        let td = document.createElement("td");
        td.innerText = text;
        tr.appendChild(td);
    }
    addTd(tr, this.name);
    addTd(tr, this.size);
    addTd(tr, this.date);
    fileTable.appendChild(tr);
    this.tr2obj.set(tr, this);
    this.trObj = tr;

    tr.addEventListener("click", function (event) {
        const obj = NetFile.prototype.tr2obj.get(this);
        obj.selected = !obj.selected;
        if (obj.selected !== false) {
            this.style.backgroundColor = "red";
            NetFile.prototype.selectedSet.add(obj);
            return;
        }
        this.style.backgroundColor = "transparent";
        NetFile.prototype.selectedSet.delete(obj);
    }, false);
};
NetFile.prototype.clearDisplayList = function () {
    fileTable.innerHTML = "";
};
function removeFile(name) {
    var node;
    node = name2fileName[name];
    fileNameCol.removeChild(node);
    node = name2fileSize[name];
    fileSizeCol.removeChild(node);
    node = name2fileDate[name];
    fileDateCol.removeChild(node);
}

function nbyte2string(nbyte) {
    var step = 0;
    var m = nbyte;
    var n = 0;
    var step2Letter = ["Bytes", "KBytes", "MBytes", "GBytes", "TBytes"];
    while (true) {
        n = m % 1024;
        m = Math.floor(m / 1024);
        if (m === 0 || step >= 4) {
            break;
        }
        ++step;
    }
    if (step === 0) {
        return n + "Byte";
    }
    nbyte = m + (n / 1024.0).toFixed(2);
    return nbyte.toString() + step2Letter[step];
}

// load file list from server


// resize table
let draggingFlag = false;
var theads = document.getElementById("file-table-header").getElementsByTagName("th");
for (let i = 0; i < theads.length - 1; ++i) {
    theads[i].addEventListener("mousemove", function (event) {
        const lower = this.offsetWidth * 0.98, upper = this.offsetWidth * 1.15;
        const cur = event.clientX - this.getBoundingClientRect().left;
        if (lower <= cur && cur <= upper) {
            this.style.cursor = "col-resize";
        } else {
            this.style.cursor = "pointer";
        }
        if (this.dragging === true) {
            this.style.width = cur - 15 + "px";
        }
    }, false);
    theads[i].addEventListener("mousedown", function (event) {
        this.dragging = true;
        draggingFlag = true;
    }, false);
    theads[i].addEventListener("mouseout", function (event) {
        this.dragging = false;
        draggingFlag = false;
    }, false);
    theads[i].addEventListener("mouseup", function (event) {
        this.dragging = false;
        draggingFlag = false;
    }, false);
}
document.addEventListener("selectstart", function (event) {
    return draggingFlag !== true;
}, false);

// upload file
const uploadButton = document.getElementById("upload-button");
const uploadPanel = document.getElementById("upload-panel");
const progressBar = uploadPanel.getElementsByTagName("progress")[0];
const uploadMsg = uploadPanel.getElementsByTagName("span")[0];
const virtualButton = document.getElementById("upload-virtual-button");
const actualSubmit = document.getElementById("actual-submit");
const uploadFormCurDir = document.getElementById("upload-current-dir");

virtualButton.addEventListener("click", function (event) {
    document.getElementById("uploading-file").click();
}, false);
uploadButton.addEventListener("click", function (event) {
    const fileObj = document.getElementById("uploading-file").files[0];
    if (fileObj) {
        const url = "/netdisk/upload?dir=" + curFileDir;
        const form = new FormData();
        form.append('file', fileObj);
        uploadFormCurDir['value'] = curFileDir;
        form.append('dir', uploadFormCurDir.nodeValue);
        // form.append('dir', new Blob([curFileDir], { type: "text/plain" }));
        const xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        // xhr.setRequestHeader("Content-Type", "multipart/form-data");
        xhr.send(form);

        xhr.addEventListener("load", function (event) {
            if (xhr.status === 200) {
                uploadMsg.innerText = "File uploaded...";
                refreshFileList(xhr.responseText);
            }
        }, false);

        xhr.addEventListener("error", function (event) {
            uploadMsg.innerText = "Upload failed...";
        }, false);

        xhr.addEventListener("progress", function (event) {
            if (event.lengthComputable) {
                progressBar.max = event.total;
                progressBar.value = event.loaded;
                const loading = Math.round(event.loaded / event.total * 100);
            }
        }, false);
    } else {
        uploadMsg.innerText = "No file selected...";
    }
}, false);

document.getElementById("upload").addEventListener("click", function () {
    if (uploadPanel.style.display === "none") {
        uploadPanel.style.display = "flex";
    } else {
        uploadPanel.style.display = "none";
    }
}, false);


// file list
let curFileDir = "/";
function refreshFileList() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/netdisk/getDirInfo?dir=" + curFileDir, true);
    // xhr.setRequestHeader("Content-Type", "application/json;charset=utf-8");
    // json = JSON.stringify();
    // console.log(json);
    // const obj = {
    //     dir: curFileDir,
    // };
    xhr.send(null);

    xhr.onload = function (event) {
        if (this.status === 200) {
            const fileList = JSON.parse(this.responseText);
            NetFile.prototype.clearDisplayList();
            for (let i = 0; i < fileList.length; ++i) {
                const file = fileList[i];
                (new NetFile(file.name, file.fileSize, file.dateVal, file.dir)).putOnPage();
            }
        }
    }
}

document.getElementById("refresh-list").addEventListener("click", function (event) {
    refreshFileList();
}, false);