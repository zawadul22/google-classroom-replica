FROM node:23-alpine3.20
WORKDIR /app

COPY . .
EXPOSE 5173

RUN npm install

CMD ["npm", "run", "dev", "--", "--host"]