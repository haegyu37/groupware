import LogoutBtn from "../../LogoutBtn";
import useStore from "../../../store";
import styled, {css} from "styled-components";
import defaultProfileImage from "../../../IMAGES/profile.jpg";

function Profile() {
    const {myAccount} = useStore(state => state)
    const isAdmin = myAccount.authority === "ADMIN";

    const profileImg = "http://localhost:8080/member/image?imageName=" + myAccount.image;

    return (
        <Wrapper>
            <UpperText>
                <p>안녕하세요,&nbsp;</p> <p style={{fontWeight: "bold"}}>{myAccount.name}</p><p>님!</p>
            </UpperText>
            <ProfileCard isAdmin={isAdmin}>
                <ProfileImg isAdmin={isAdmin} src={myAccount.image ? profileImg : defaultProfileImage } />
                <Info>
                    <p>{myAccount.name} {myAccount.position}</p>
                    <p style={{margin:"10px 0"}}>부서 : {myAccount.team}</p>
                    <p>사번 : {myAccount.no}</p>
                    <LogoutBtn/>
                </Info>
            </ProfileCard>
        </Wrapper>
    )
}
export default Profile

const Wrapper = styled.div`
  margin: 30px 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
`
const UpperText = styled.div`
  display: flex;
  flex-direction: row;
  color: black;
  font-size: 20px;
  margin-bottom: 15px;
`
const ProfileCard = styled.div`
  width: 350px;
  height: 230px;
  display: flex;
  flex-direction: row;
  border-radius: 15px;
  background: rgba(125, 121, 242, 0.1);
  border: solid 1px rgba(68, 41, 242, 0.6);
  ${props => props.isAdmin && css`
    background: rgba(250, 62, 12, 0.1);
    border: 1px solid rgba(250, 62, 12, 0.4);
  `}
`
const ProfileImg = styled.img`
  width: 120px;
  height: 150px;
  margin: 35px;
  border: ${props => props.isAdmin ? "3px solid rgba(250, 62, 12, 0.4)" : "3px solid rgba(68, 41, 242, 0.4)"};
`
const Info = styled.div`
  color: black;
  margin: 50px 10px;
  p {margin: 5px 0;}
`