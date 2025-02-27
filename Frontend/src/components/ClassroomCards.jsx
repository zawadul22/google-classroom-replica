import { Card, CardContent, CardDescription, CardHeader, CardTitle } from './ui/card'

import avatarImage from "../assets/man.png";
import { Avatar, AvatarImage } from './ui/avatar'
import teacherPoster from "/teacher_poster.jpg";
import studentPoster from "/student_poster.jpg";
import { NavLink } from 'react-router';

const ClassroomCards = ({ index, classroom, type }) => {
  return (
    <NavLink to={`/${type}/${index}`}>
      <Card className="custom-card" style={{ backgroundImage: `url(${type === "teacher" ? teacherPoster : studentPoster})` }}>
        <CardHeader>
          <CardTitle className="text-white">
            {classroom?.classroomName}
          </CardTitle>
          <CardDescription className="text-white">
            Section {classroom?.section}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="relative">
            <p className="absolute top-5 font-semibold truncate w-48">{classroom?.creator}</p>
            <Avatar className="absolute right-0 -bottom-12 w-20 h-20">
              <AvatarImage src={avatarImage} />
            </Avatar>
            <p className="absolute top-11">{classroom?.totalUsers} participants</p>
          </div>
        </CardContent>
      </Card>
    </NavLink>
  )
}

export default ClassroomCards
