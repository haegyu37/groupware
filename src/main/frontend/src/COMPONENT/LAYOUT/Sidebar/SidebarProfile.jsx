import LogoutBtn from "../../LogoutBtn";
import useStore from "../../../store";
import styled from "styled-components";
import defaultProfileImage from "../../../IMAGES/profile.jpg";

function SidebarProfile() {
    const {myAccount} = useStore(state => state)
    const isAdmin = myAccount?.authority === "ADMIN";

    return (
        <ProfileWrapper>
            <Profile isAdmin={isAdmin}>
                <ProfileImg isAdmin={isAdmin} src={myAccount.image ? "http://localhost:8080/member/image?imageName=" + myAccount.image : defaultProfileImage } />
                <p style={{marginTop: "20px"}}>{myAccount.name}</p>
                <p style={{margin: "5px 0"}}>{myAccount.team} / {myAccount.position}</p>
                <LogoutBtn/>
            </Profile>
        </ProfileWrapper>
    )
}

export default SidebarProfile

const ProfileWrapper = styled.div`
  height: 40%;
  display: flex;
  justify-content: center;
  align-items: center;
`
const Profile = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 200px;
  height: 300px;
  background: ${props=> props.isAdmin ? "rgba(250, 62, 12, 0.1)" : "rgba(68, 41, 242, 0.15)" };
  border-radius: 10px;
  border: ${props => props.isAdmin ? "1px solid rgba(250, 62, 12, 0.4)" : "solid 1px rgba(68, 41, 242, 0.4)"};
`
const ProfileImg = styled.img`
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  object-position: center;
  border: ${props => props.isAdmin ? "3px solid rgba(250, 62, 12, 0.4)" : "solid 3px rgba(68, 41, 242, 0.4)"};
`