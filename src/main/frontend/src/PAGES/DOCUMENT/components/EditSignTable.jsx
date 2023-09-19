import SignTableComponent from "../../../COMPONENT/SignTableComponent";
import useStore from "../../../store";


function EditSignTable({documentData, signLine}) {
    const {myAccount} = useStore(state => state)
    const signTurn1 = signLine[1];
    const signTurn2 = signLine[2];
    const signRefer = signLine[3];

    const time = new Date();
    const toDay = {
        year: time.getFullYear(),
        month: (time.getMonth() + 1).toString().padStart(2, "0"),
        day: time.getDate().toString().padStart(2, "0")
    }

    const leftData = [
        {title: "기안자", content: `${myAccount.name}`},
        {title: "기안부서", content: `${myAccount.team}`},
        {title: "기안일", content: `${toDay.year}-${toDay.month}-${toDay.day}`},
        {title: "문서번호", content: `${documentData.dno ? documentData.dno : documentData.sno}`}
    ]
    const rightData = [
        {
            signTurn: "작 성",
            sign: "",
            signName: `${myAccount.name}${myAccount.position}`
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

export default EditSignTable
