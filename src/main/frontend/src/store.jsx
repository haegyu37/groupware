import {create} from 'zustand'

const useStore = create(set => ({

    headerTitle: "메인",
    changeTitle: (e)=> set(state =>({headerTitle:e})),

    teamName: "",
    selectTeam: (e)=> set(state=>({teamName:e})),

    positionName: "",
    selectPosition: (e) => set(state => ({positionName:e})),

    account: {
        name: "",
        password: "",
        no: "",
        team: "",
        position: "",
        authority: "",
        image: ""
    },
    selectAccount: (e) => set(state =>({account:e})),

    myAccount: {
        name: "",
        no: "",
        team: "",
        position: "",
        authority: "",
        image: ""
    },
    setMyAccountInfo: (e) => set(state=>({myAccount:e})),

    signLine: {
        signTurn1: {
            id: "",
            name: "",
            position: ""
        },
        signTurn2: {
            id: "",
            name: "",
            position: ""
        },
        signRefer: {
            id: "",
            name: "",
            position: ""
        },
    },
    setSignLine: (e)=> set(state=>({signLine:e}))

}))

export default useStore