import SignTableComponent from "../../../COMPONENT/SignTableComponent";

function TempDocumentSignTable({documentData, signLine}) {
    const writer = documentData.writer;
    const signTurn1 = signLine[1];
    const signTurn2 = signLine[2];
    const signRefer = signLine[3];

    const leftData = [
        {title: "기안자", content: `${writer?.name || ""}`},
        {title: "기안부서", content: `${writer?.team || ""}`},
        {title: "기안일", content: `${documentData.createDate || ""}`},
        {title: "문서번호", content: `${documentData.sno || ""}`}
    ]

    const rightData = [
        {
            signTurn: "작 성",
            sign: "",
            signName: `${writer?.name || ""}${writer?.position || ""}`
        },
        {
            signTurn: "검 토",
            sign: "",
            signName: signTurn1 ? `${signTurn1?.name} ${signTurn1?.position || ""}` : ""
        },
        {
            signTurn: "승 인",
            sign: "",
            signName: signTurn2 ? `${signTurn2?.name} ${signTurn2?.position || ""}` : ""
        }
    ]

    return (
        <>
            <SignTableComponent leftData={leftData} rightData={rightData} signRefer={signRefer}/>
        </>
    )
}

export default TempDocumentSignTable